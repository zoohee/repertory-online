package team.luckyturkey.danceservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.luckyturkey.danceservice.controller.requestdto.PostTagRequest;
import team.luckyturkey.danceservice.controller.responsedto.StandardTagResponse;
import team.luckyturkey.danceservice.domain.entity.Tag;
import team.luckyturkey.danceservice.repository.jpa.TagRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService{

    private final TagRepository tagRepository;

    @Transactional
    @Override
    public Long saveTag(PostTagRequest postTagRequest, Long memberId) {
        Tag tag = Tag.builder()
                    .tagName(postTagRequest.getTagName())
                    .memberId(memberId)
                    .build();

        return tagRepository.save(tag).getId();
    }

    @Override
    public List<StandardTagResponse> getTagList(Long userId) {
        List<Tag> tagList = tagRepository.findByMemberId(userId);
        List<StandardTagResponse> responseList = new ArrayList<>();
        for(Tag tag: tagList){
            responseList.add(tagToStandardResponse(tag));
        }
        return responseList;
    }

    @Transactional
    @Override
    public void deleteTag(Long tagId) {
        tagRepository.deleteById(tagId);
    }

    private StandardTagResponse tagToStandardResponse(Tag tag){
        return StandardTagResponse.builder()
                .tagId(tag.getId())
                .tagName(tag.getTagName())
                .build();
    }
}
