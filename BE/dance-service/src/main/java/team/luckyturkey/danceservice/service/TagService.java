package team.luckyturkey.danceservice.service;

import team.luckyturkey.danceservice.entity.Tag;

import java.util.List;

public interface TagService {
    Long saveTag(Tag tag);

    void deleteTag(Long tagId);

    List<Tag> getTagList(Long userId);
}
