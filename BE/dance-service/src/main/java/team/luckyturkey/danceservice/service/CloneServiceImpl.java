package team.luckyturkey.danceservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.luckyturkey.danceservice.controller.requestdto.PostCloneRequest;
import team.luckyturkey.danceservice.controller.responsedto.StandardSourceResponse;
import team.luckyturkey.danceservice.domain.entity.CloneSource;
import team.luckyturkey.danceservice.domain.entity.CloneSourceDetail;
import team.luckyturkey.danceservice.domain.entity.Source;
import team.luckyturkey.danceservice.domain.entity.Tag;
import team.luckyturkey.danceservice.domain.entity.id.CloneSourceDetailPK;
import team.luckyturkey.danceservice.repository.jpa.CloneSourceRepository;
import team.luckyturkey.danceservice.repository.jpa.SourceRepository;
import team.luckyturkey.danceservice.repository.jpa.TagRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CloneServiceImpl implements CloneService{

    private final CloneSourceRepository cloneSourceRepository;
    private final SourceRepository sourceRepository;
    private final TagRepository tagRepository;


    @Transactional
    @Override
    public void clone(PostCloneRequest postCloneRequest) {
        Long sourceId = postCloneRequest.getOriginId();
        Long memberId = postCloneRequest.getMemberId();

        Source source = sourceRepository.findById(sourceId)
                            .orElseThrow(() -> new IllegalArgumentException("source not exist"));


        List<String> tagNameList = source.getTagList().stream()
                .map(Tag::getTagName)
                .toList();

//        modifyTagList(tagList, memberId);

        CloneSource cloneSource = CloneSource.builder()
                .source(source)
                .memberId(memberId)
                .tagName(tagNameList)
                .cloneDate(LocalDateTime.now())
                .build();

        CloneSourceDetail cloneSourceDetail = CloneSourceDetail.builder()
                .cloneSourceDetailPK(new CloneSourceDetailPK())
                .sourceName(source.getSourceName())
                .sourceLength(source.getSourceLength())
                .sourceCount(0)
                .sourceStart(source.getSourceStart())
                .sourceEnd(source.getSourceEnd())
                .build();

        cloneSource.setCloneSourceDetail(cloneSourceDetail);
        cloneSourceDetail.setCloneSource(cloneSource);
        cloneSourceRepository.save(cloneSource);
    }

    // 이거 필요 없는데...?
    // 대충 태그 값이 클론한 사용자도 가질 수 있게 삽입하는 연산들...
    private void modifyTagList(List<Tag> tagList, Long memberId){
        Set<String> tagNameSet = tagRepository.findTagNameByMemberId(memberId);

        for(Tag tag: tagList){
            if(tagNameSet.contains(tag.getTagName())) continue;

            Tag newTag = Tag.builder()
                    .tagName(tag.getTagName())
                    .memberId(memberId)
                    .build();

            tagRepository.save(newTag);
        }
    }

    @Deprecated
    private StandardSourceResponse mapToStandardResponse(CloneSource cloneSource){
        return StandardSourceResponse.builder()
                .sourceId(cloneSource.getId())
                .memberId(cloneSource.getMemberId())
                .sourceName(cloneSource.getSourceName())
                .sourceStart(cloneSource.getSourceStart())
                .sourceEnd(cloneSource.getSourceEnd())
                .sourceLength(cloneSource.getSourceLength())
                .sourceCount(cloneSource.getSourceCount())
                .sourceUrl(cloneSource.getSourceUrl())
                .tagList(null)
                .build();
    }
}
