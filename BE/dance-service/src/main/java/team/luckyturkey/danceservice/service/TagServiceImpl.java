package team.luckyturkey.danceservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.luckyturkey.danceservice.entity.Tag;
import team.luckyturkey.danceservice.repository.jpa.SourceTagRepository;
import team.luckyturkey.danceservice.repository.jpa.TagRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService{

    private final TagRepository tagRepository;

    @Transactional
    @Override
    public Long saveTag(Tag tag) {
        return tagRepository.save(tag).getId();
    }

    @Override
    public List<Tag> getTagList(Long userId) {
        return tagRepository.findByMemberId(userId);
    }

    @Transactional
    @Override
    public void deleteTag(Long tagId) {
        tagRepository.deleteById(tagId);
    }
}
