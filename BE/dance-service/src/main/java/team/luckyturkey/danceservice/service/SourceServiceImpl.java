package team.luckyturkey.danceservice.service;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;
import team.luckyturkey.danceservice.api.CommunityApi;
import team.luckyturkey.danceservice.controller.requestdto.PatchSourceRequest;
import team.luckyturkey.danceservice.controller.requestdto.PatchSourceStatusRequest;
import team.luckyturkey.danceservice.controller.requestdto.PostSourceRequest;
import team.luckyturkey.danceservice.controller.responsedto.StandardSourceResponse;
import team.luckyturkey.danceservice.controller.responsedto.StandardTagResponse;
import team.luckyturkey.danceservice.domain.entity.Source;
import team.luckyturkey.danceservice.domain.entity.SourceDetail;
import team.luckyturkey.danceservice.domain.entity.Tag;
import team.luckyturkey.danceservice.domain.entity.id.SourceDetailPK;
import team.luckyturkey.danceservice.domain.entity.id.SourceTagPK;
import team.luckyturkey.danceservice.domain.entity.mapper.SourceTag;
import team.luckyturkey.danceservice.event.SourceDisabledEvent;
import team.luckyturkey.danceservice.event.SourceEnabledEvent;
import team.luckyturkey.danceservice.event.SourceTagModifiedEvent;
import team.luckyturkey.danceservice.repository.jpa.SourceDetailRepository;
import team.luckyturkey.danceservice.repository.jpa.SourceRepository;
import team.luckyturkey.danceservice.repository.jpa.SourceTagRepository;
import team.luckyturkey.danceservice.repository.jpa.TagRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static team.luckyturkey.danceservice.domain.FeedType.SOURCE;

/**todo: 캐시와 결합 crud
 * */
@Service
@RequiredArgsConstructor
public class SourceServiceImpl implements SourceService{

    private final SourceRepository sourceRepository;
    private final SourceDetailRepository sourceDetailRepository;
    private final TagRepository tagRepository;
    private final SourceTagRepository sourceTagRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final CommunityApi communityApi;

    @Value("${test.environment.sourceUrl}")
    private String TEST_SOURCE_URL;


    @Override
    public List<StandardSourceResponse> getMySourceList(Long memberId) {
        List<Source> sourceList = sourceRepository.findByMemberId(memberId);
        List<StandardSourceResponse> responseList = new ArrayList<>();

        for(Source source: sourceList) {
            responseList.add(sourceToStandardResponse(source));
        }
        return responseList;
    }

    @Override
    public List<StandardSourceResponse> getSourceList(List<Long> sourceIdList) {
        List<Source> sourceList = sourceRepository.findByIdIn(sourceIdList);
        List<StandardSourceResponse> responseList = new ArrayList<>();

        for(Source source: sourceList) {
            responseList.add(sourceToStandardResponse(source));
        }
        return responseList;
    }

    @Override
    public List<StandardSourceResponse> searchSource(String keyword) {
        List<Source> sourceList = sourceRepository.findBySourceNameLike(keyword);
        List<StandardSourceResponse> responseList = new ArrayList<>();

        for(Source source: sourceList) {
            responseList.add(sourceToStandardResponse(source));
        }
        return responseList;
    }

    @Override
    public StandardSourceResponse getSource(Long sourceId) {

        Source source = sourceRepository.findByIdWithDetail(sourceId)
                                            .orElse(new Source());

        if(source.getId() == null) return new StandardSourceResponse();
        else{
            return sourceToStandardResponse(source);
        }
    }

    @Transactional
    @Override
    public StandardSourceResponse saveSource(PostSourceRequest postSourceRequest, MultipartFile sourceVideo, Long memberId) {

        SourceDetail sourceDetail = SourceDetail.builder()
                                        .id(new SourceDetailPK())
                                        .sourceCount(0)
                                        .isSourceOpen(false)
                                        .sourceStart(postSourceRequest.getStart())
                                        .sourceEnd(postSourceRequest.getEnd())
                                        .sourceName(postSourceRequest.getSourceName())
                                        .sourceLength(postSourceRequest.getSourceLength())
                                        .build();

        /** todo: video should be uploaded and url should be set in source obj
         * */
        Source source = Source.builder()
                            .memberId(memberId)
                            .sourceDetail(sourceDetail)
                            .sourceDate(LocalDateTime.now())
                            .sourceUrl(TEST_SOURCE_URL + sourceVideo.getOriginalFilename())
                            .build();

        sourceDetail.setSource(source);

        mapSourceTag(source, postSourceRequest.getTagNameList(), memberId);

        Source savedSource = sourceRepository.save(source);
        return sourceToStandardResponse(savedSource);
    }


    @Transactional
    @Override
    public Long updateSource(Long sourceId, PatchSourceRequest patchSourceRequest, Long memberId) {
        Source source = Source.builder().id(sourceId).build();

        List<SourceTag> originTagList = sourceTagRepository.findTagListByMemberIdAndSourceId(sourceId, memberId);
        List<String> newTagNameList = patchSourceRequest.getTagNameList();

        boolean isTagModified = false;
        // 삭제된 태그들 연관관계 수정 시작
        for(SourceTag sourceTag: originTagList){
            if(newTagNameList.contains(sourceTag.getTag().getTagName())) continue;
            isTagModified = true;
            sourceTagRepository.deleteById(new SourceTagPK(sourceId, sourceTag.getTag().getId()));
        }
        // 삭제된 태그들 연관관계 수정 끝

        SourceDetail sourceDetail = SourceDetail.builder()
                                        .id(new SourceDetailPK(sourceId))
                                        .source(source)
                                        .sourceName(patchSourceRequest.getSourceName())
                                        .sourceLength(patchSourceRequest.getSourceLength())
                                        .sourceStart(patchSourceRequest.getStart())
                                        .sourceEnd(patchSourceRequest.getEnd())
                                        .build();
        SourceDetail savedSourceDetail = sourceDetailRepository.save(sourceDetail);

        // 연관 관계 수정 시작
        List<SourceTag> sourceTagList = mapSourceTag(source, newTagNameList, memberId);
        sourceTagRepository.saveAll(sourceTagList);
        // 연관 관계 수정 끗

        // 태그 변경 사항 있을 시, 이벤트 발행 -> 캐시 db 수정
        // new tag list가 origin tag list의 부분집합 이라면 -> 추가된 태그는 없음
        Set<String> tagNames = originTagList.stream()
                .map(sourceTag -> sourceTag.getTag().getTagName())
                .collect(Collectors.toSet());

        List<String> addedTagNameList = new ArrayList<>(newTagNameList);
        addedTagNameList.removeAll(tagNames);

        List<String> deletedTagNameList = new ArrayList<>(tagNames);
        deletedTagNameList.removeAll(newTagNameList);

        if(!addedTagNameList.isEmpty()) isTagModified = true;

        if(isTagModified){
            try{
                eventPublisher.publishEvent(new SourceTagModifiedEvent(addedTagNameList, deletedTagNameList, memberId, sourceId));
            }catch (Exception e){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
        }
        return savedSourceDetail.getId().getSourceId();
    }

    @Transactional
    @Override
    public void deleteSource(Long sourceId) {
        Source source = sourceRepository.findById(sourceId)
                .orElseThrow(() -> new IllegalArgumentException("source not exist"));

        // cascade 설정으로, source를 지우면, clone source 같이 지워짐
        sourceRepository.delete(source);

        List<String> tagNameList = source.getTagList().stream()
                .map(Tag::getTagName)
                .toList();
        Long memberId = source.getMemberId();

        try {
            eventPublisher.publishEvent(new SourceDisabledEvent(tagNameList, memberId, sourceId));
        }catch (Exception e){
            sourceRepository.save(source);
        }
    }

    @Transactional
    @Override
    public Long updateSourceStatus(Long sourceId, PatchSourceStatusRequest patchSourceStatusRequest, Long memberId) {
        SourceDetail sourceDetail = sourceDetailRepository.findById(sourceId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid sourceId: " + sourceId));

        boolean newStatus = patchSourceStatusRequest.isAvailable();

        sourceDetail.setSourceOpen(newStatus);

        Source source = sourceDetail.getSource();
        List<Tag> tagList = source.getTagList();

        List<String> tagNameList = tagList.stream()
                .map(Tag::getTagName)
                .toList();


        // community connection start
        ResponseEntity<Void> response = communityApi.postFeed(sourceId, SOURCE);
        if(response.getStatusCode() != HttpStatus.OK){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        // community connection end

        // cache server update start
        try {
            if(newStatus) eventPublisher.publishEvent(new SourceEnabledEvent(tagNameList, sourceId, memberId));
            else eventPublisher.publishEvent(new SourceDisabledEvent(tagNameList, sourceId, memberId));
        } catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        // cache server update end

        return sourceDetail.getId().getSourceId();
    }

    private StandardSourceResponse sourceToStandardResponse(Source source){
        List<StandardTagResponse> tagResponseList = new ArrayList<>();
        for(Tag tag: source.getTagList()){
            tagResponseList.add(tagToStandardResponse(tag));
        }
        //todo: need null check
        return StandardSourceResponse.builder()
                .sourceId(source.getId())
                .memberId(source.getMemberId())
                .sourceName(source.getSourceName())
                .sourceStart(source.getSourceStart())
                .sourceEnd(source.getSourceEnd())
                .sourceLength(source.getSourceLength())
                .sourceCount(source.getSourceCount())
                .sourceUrl(source.getSourceUrl())
                .tagList(tagResponseList)
                .build();
    }

    private StandardTagResponse tagToStandardResponse(Tag tag){
        return StandardTagResponse.builder()
                .tagId(tag.getId())
                .memberId(tag.getMemberId())
                .tagName(tag.getTagName())
                .build();
    }

    private List<SourceTag> mapSourceTag(Source source, List<String> tagNameList, Long memberId) {
        List<SourceTag> sourceTagList = new ArrayList<>();
        for(String tagName: tagNameList){
            Tag tag;

            if(!tagRepository.existsTagByTagNameAndMemberId(tagName, memberId)) {
                tag = Tag.builder()
                        .memberId(memberId)
                        .tagName(tagName)
                        .build();
                tagRepository.save(tag);
            }
            else{
                tag = tagRepository.findByTagNameAndMemberId(tagName, memberId);
            }

            SourceTag sourceTag = SourceTag.builder().
                                        id(new SourceTagPK(source.getId(), tag.getId()))
                                        .source(source)
                                        .tag(tag)
                                        .build();
            source.getSourceTagList().add(sourceTag);
            tag.getSourceTagList().add(sourceTag);
            sourceTagList.add(sourceTag);
        }
        return sourceTagList;
    }
}
