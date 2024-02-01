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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//todo: S3 should be integreted
//todo: when repertory is open status -> send request to community service to add row to feed table?
@Service
@RequiredArgsConstructor
public class RepertoryServiceImpl implements RepertoryService{

    private final RepertoryRepository repertoryRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Value("${test.environment.repertoryUrl}")
    private String TEST_URL;


    //todo: how to notify failure to controller
    @Transactional
    @Override
    public StandardRepertoryResponse saveRepertory(
            PostRepertoryRequest postRepertoryRequest,
            MultipartFile repertoryVideo,
            MultipartFile repertoryThumbnailVideo,
            Long memberId
    ) {

        //todo: should get from s3
        String repertoryUrl = TEST_URL + repertoryVideo.getName();
        String repertoryThumbnailUrl = TEST_URL + repertoryThumbnailVideo.getName();

        Repertory repertory = Repertory.builder()
                .repertoryName(postRepertoryRequest.getRepertoryName())
                .memberId(memberId)
                .sourceList(postRepertoryRequest.getSourceIdList())
                .isRepertoryOpen(postRepertoryRequest.isRepertoryOpen())
                .repertoryUrl(repertoryUrl)
                .repertoryThumbnailUrl(repertoryThumbnailUrl)
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
        //todo: should get from s3
        String modifiedVideoUrl = TEST_URL + "/modified/" + repertoryVideo.getName();

        Repertory modifiedRepertory = repertoryRepository.findAndUpdateVideoUrl(repertoryId, modifiedVideoUrl);
        return repertoryToStandardResponse(modifiedRepertory);
    }

    @Override
    public Long modifyRepertoryStatus(Long repertoryId, PatchRepertoryStatusRequest patchRepertoryStatusRequest) {
        Repertory modifiedRepertory = repertoryRepository.findAndUpdateIsRepertoryOpen(repertoryId,  patchRepertoryStatusRequest.getIsAvailable());

        return modifiedRepertory.getId();
    }

    @Override
    public List<StandardRepertoryResponse> searchByName(String keyword) {
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

    private StandardRepertoryResponse repertoryToStandardResponse(Repertory repertory) {
        return StandardRepertoryResponse.builder()
                .repertoryId(repertory.getId())
                .repertoryName(repertory.getRepertoryName())
                .repertoryUrl(repertory.getRepertoryUrl())
                .repertoryThumbnailUrl(repertory.getRepertoryThumbnailUrl())
                .build();
    }

}
