package team.luckyturkey.danceservice.service;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import team.luckyturkey.danceservice.controller.requestdto.PatchSourceRequest;
import team.luckyturkey.danceservice.controller.requestdto.PostSourceRequest;
import team.luckyturkey.danceservice.controller.responsedto.StandardSourceResponse;
import team.luckyturkey.danceservice.controller.responsedto.StandardTagResponse;
import team.luckyturkey.danceservice.domain.entity.Source;
import team.luckyturkey.danceservice.domain.entity.SourceDetail;
import team.luckyturkey.danceservice.domain.entity.SourceTag;
import team.luckyturkey.danceservice.domain.entity.Tag;
import team.luckyturkey.danceservice.domain.entity.id.SourceDetailPK;
import team.luckyturkey.danceservice.domain.entity.id.SourceTagPK;
import team.luckyturkey.danceservice.repository.jpa.SourceDetailRepository;
import team.luckyturkey.danceservice.repository.jpa.SourceRepository;
import team.luckyturkey.danceservice.repository.jpa.SourceTagRepository;
import team.luckyturkey.danceservice.repository.jpa.TagRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**todo: 캐시와 결합 crud
 * */
@Service
@RequiredArgsConstructor
public class SourceServiceImpl implements SourceService{

    private final SourceRepository sourceRepository;
    private final SourceDetailRepository sourceDetailRepository;
    private final TagRepository tagRepository;
    private final SourceTagRepository sourceTagRepository;

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
                                        .sourceIsOpen(false)
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

        List<SourceTag> deleteSourceTagList = sourceTagRepository.findTagListByMemberIdAndSourceId(sourceId, memberId);

        for(SourceTag sourceTag: deleteSourceTagList){
            if(patchSourceRequest.getTagNameList().contains(sourceTag.getTag().getTagName())) continue;
            sourceTagRepository.deleteById(new SourceTagPK(sourceId, sourceTag.getTag().getId()));
        }
        SourceDetail sourceDetail = SourceDetail.builder()
                                        .id(new SourceDetailPK(sourceId))
                                        .source(source)
                                        .sourceName(patchSourceRequest.getSourceName())
                                        .sourceLength(patchSourceRequest.getSourceLength())
                                        .sourceStart(patchSourceRequest.getStart())
                                        .sourceEnd(patchSourceRequest.getEnd())
                                        .build();
        SourceDetail savedSourceDetail = sourceDetailRepository.save(sourceDetail);


        List<SourceTag> sourceTagList = mapSourceTag(source, patchSourceRequest.getTagNameList(), memberId);
        sourceTagRepository.saveAll(sourceTagList);
        return savedSourceDetail.getId().getSourceId();
    }

    @Transactional
    @Override
    public void deleteSource(Long sourceId) {
        sourceRepository.deleteById(sourceId);
    }

    private StandardSourceResponse sourceToStandardResponse(Source source){
        List<StandardTagResponse> tagResponseList = new ArrayList<>();
        for(Tag tag: source.getTagList()){
            tagResponseList.add(tagToStandardResponse(tag));
        }
        //todo: need null check
        return StandardSourceResponse.builder()
                .sourceId(source.getId())
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
