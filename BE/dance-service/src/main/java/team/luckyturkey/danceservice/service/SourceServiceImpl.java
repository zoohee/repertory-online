package team.luckyturkey.danceservice.service;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import team.luckyturkey.danceservice.controller.requestdto.PatchSourceRequest;
import team.luckyturkey.danceservice.controller.requestdto.PostSourceRequest;
import team.luckyturkey.danceservice.entity.Source;
import team.luckyturkey.danceservice.entity.SourceDetail;
import team.luckyturkey.danceservice.entity.SourceTag;
import team.luckyturkey.danceservice.entity.Tag;
import team.luckyturkey.danceservice.entity.id.SourceDetailPK;
import team.luckyturkey.danceservice.entity.id.SourceTagPK;
import team.luckyturkey.danceservice.repository.jpa.SourceDetailRepository;
import team.luckyturkey.danceservice.repository.jpa.SourceRepository;
import team.luckyturkey.danceservice.repository.jpa.SourceTagRepository;
import team.luckyturkey.danceservice.repository.jpa.TagRepository;

import java.time.LocalDateTime;
import java.util.List;

/**todo: source 정보 조회 시 tag List 포함
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
    public List<Source> getMySourceList(Long memberId) {
        return sourceRepository.findByMemberId(memberId);
    }

    @Override
    public List<Source> searchSource(String keyword) {
        return sourceRepository.findBySourceNameLike(keyword);
    }

    @Override
    public Source getSource(Long sourceId) {
        return sourceRepository.findByIdWithDetail(sourceId)
                .orElse(new Source());
    }

    @Transactional
    @Override
    public Source saveSource(PostSourceRequest postSourceRequest, MultipartFile sourceVideo, Long memberId) {

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
        Source savedSource = sourceRepository.save(source);

        List<String> tagNameList = postSourceRequest.getTagList();
        for(String tagName: tagNameList){
            Tag tag;

            if(!tagRepository.existsTagByTagNameAndMemberId(tagName, memberId)){
                tag = Tag.builder()
                        .memberId(memberId)
                        .tagName(tagName)
                        .build();
                tag = tagRepository.save(tag);
            }
            else{
                tag = tagRepository.findByTagNameAndMemberId(tagName, memberId);
            }

            SourceTag sourceTag = SourceTag.builder()
                                    .id(new SourceTagPK())
                                    .source(savedSource)
                                    .tag(tag)
                                    .build();

            sourceTagRepository.save(sourceTag);

        }
        return savedSource;
    }

    @Transactional
    @Override
    public Long updateSource(Long sourceId, PatchSourceRequest patchSourceRequest) {
        SourceDetail sourceDetail = SourceDetail.builder()
                                        .id(new SourceDetailPK(sourceId))
                                        .sourceName(patchSourceRequest.getSourceName())
                                        .sourceLength(patchSourceRequest.getSourceLength())
                                        .sourceStart(patchSourceRequest.getStart())
                                        .sourceEnd(patchSourceRequest.getEnd())
                                        .build();

        SourceDetail savedSourceDetail = sourceDetailRepository.save(sourceDetail);
        return savedSourceDetail.getId().getSourceId();
    }

    @Transactional
    @Override
    public void deleteSource(Long sourceId) {
        sourceRepository.deleteById(sourceId);
    }
}
