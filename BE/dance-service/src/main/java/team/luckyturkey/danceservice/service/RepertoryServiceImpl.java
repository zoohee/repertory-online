package team.luckyturkey.danceservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import team.luckyturkey.danceservice.controller.requestdto.PatchRepertoryStatusRequest;
import team.luckyturkey.danceservice.controller.requestdto.PostRepertoryRequest;
import team.luckyturkey.danceservice.controller.responsedto.StandardRepertoryResponse;
import team.luckyturkey.danceservice.domain.document.Repertory;
import team.luckyturkey.danceservice.event.RepertoryDeletedEvent;
import team.luckyturkey.danceservice.event.RepertorySavedEvent;
import team.luckyturkey.danceservice.repository.nosql.repertory.RepertoryRepository;
import team.luckyturkey.danceservice.util.S3Uploader;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//todo: when repertory is open status -> send request to community service to add row to feed table?
@Service
@RequiredArgsConstructor
public class RepertoryServiceImpl implements RepertoryService{

    private final RepertoryRepository repertoryRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final S3Uploader s3Uploader;


    //todo: how to notify failure to controller
    @Transactional
    @Override
    public StandardRepertoryResponse saveRepertory(
            PostRepertoryRequest postRepertoryRequest,
            MultipartFile repertoryVideo,
            MultipartFile repertoryThumbnailVideo,
            Long memberId
    ) {

        String repertoryUrl = s3Uploader.uploadFile(repertoryVideo);
        String repertoryThumbnailUrl = s3Uploader.uploadFile(repertoryThumbnailVideo);

        Repertory repertory = Repertory.builder()
                .repertoryName(postRepertoryRequest.getRepertoryName())
                .memberId(memberId)
                .sourceList(postRepertoryRequest.getSourceIdList())
                .isRepertoryOpen(postRepertoryRequest.isRepertoryOpen())
                .repertoryUrl(repertoryUrl)
                .repertoryThumbnailUrl(repertoryThumbnailUrl)
                .repertoryDate(LocalDateTime.now())
                .build();

        // mongo transaction
        Repertory savedRepertory = repertoryRepository.saveWithSequence(repertory);

        try {
            applicationEventPublisher.publishEvent(new RepertorySavedEvent(savedRepertory));
        } catch (Exception e) {
            repertoryRepository.deleteById(savedRepertory.getId());
            return StandardRepertoryResponse.builder().repertoryId(-1L).build();
        }

        return repertoryToStandardResponse(savedRepertory);
    }

    @Override
    public List<StandardRepertoryResponse> getRepertoryList(Long memberId) {
        List<Repertory> repertoryList = repertoryRepository.findByMemberId(memberId);
        List<StandardRepertoryResponse> response = new ArrayList<>();

        for(Repertory repertory: repertoryList){
            response.add(repertoryToStandardResponse(repertory));
        }
        return response;
    }


    //todo: how to notify failure to controller
    @Transactional
    @Override
    public Long deleteRepertory(Long repertoryId) {
        Repertory deletedRepertory = repertoryRepository.findAndDeleteById(repertoryId);
        try {
            applicationEventPublisher.publishEvent(new RepertoryDeletedEvent(deletedRepertory));
        }catch (Exception e){
            repertoryRepository.save(deletedRepertory);
        }
        return repertoryId;
    }

    @Transactional
    @Override
    public StandardRepertoryResponse modifyRepertoryVideo(Long repertoryId, MultipartFile repertoryVideo) {
        // TODO: s3 기존 객체 삭제 구현
//        String repertoryUrl = s3Uploader.uploadFile(repertoryVideo);
        String modifiedVideoUrl = s3Uploader.uploadFile(repertoryVideo);

        Repertory modifiedRepertory = repertoryRepository.findAndUpdateVideoUrl(repertoryId, modifiedVideoUrl);
        return repertoryToStandardResponse(modifiedRepertory);
    }

    @Override
    public Long modifyRepertoryStatus(Long repertoryId, PatchRepertoryStatusRequest patchRepertoryStatusRequest) {
        Repertory modifiedRepertory = repertoryRepository.findAndUpdateIsRepertoryOpen(repertoryId,  patchRepertoryStatusRequest.getIsAvailable());

        return modifiedRepertory.getId();
    }

    @Override
    public List<StandardRepertoryResponse> searchRepertory(String keyword) {
        List<Repertory> repertoryList = repertoryRepository.findByRepertoryNameContaining(keyword);
        List<StandardRepertoryResponse> response = new ArrayList<>();

        for(Repertory repertory: repertoryList){
            response.add(repertoryToStandardResponse(repertory));
        }
        return response;
    }

    @Override
    public StandardRepertoryResponse getRepertory(Long repertoryId) {
        Repertory repertory = repertoryRepository.findById(repertoryId)
                                            .orElseThrow(() -> new IllegalArgumentException("no such repertory"));

        return repertoryToStandardResponse(repertory);
    }

    @Override
    public List<StandardRepertoryResponse> getRepertoriesBySources(List<Long> sourceIdList) {
        List<Repertory> repertoryList = repertoryRepository.findAllById(sourceIdList);
        List<StandardRepertoryResponse> responseList = new ArrayList<>();

        for(Repertory source: repertoryList) {
            responseList.add(repertoryToStandardResponse(source));
        }
        return responseList;
    }

    private StandardRepertoryResponse repertoryToStandardResponse(Repertory repertory) {
        return StandardRepertoryResponse.builder()
                .repertoryId(repertory.getId())
                .memberId(repertory.getMemberId())
                .repertoryName(repertory.getRepertoryName())
                .repertoryUrl(repertory.getRepertoryUrl())
                .repertoryThumbnailUrl(repertory.getRepertoryThumbnailUrl())
                .repertoryDate(repertory.getRepertoryDate())
                .build();
    }

}
