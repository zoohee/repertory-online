package team.luckyturkey.danceservice.service;

import team.luckyturkey.danceservice.controller.requestdto.PostTagRequest;
import team.luckyturkey.danceservice.controller.responsedto.StandardTagResponse;

import java.util.List;

public interface TagService {
    Long saveTag(PostTagRequest postTagRequest, Long userId);

    void deleteTag(Long tagId);

    List<StandardTagResponse> getTagList(Long userId);
}
