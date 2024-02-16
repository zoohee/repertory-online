package team.luckyturkey.danceservice.service;

import team.luckyturkey.danceservice.controller.requestdto.PostCloneRequest;
import team.luckyturkey.danceservice.controller.responsedto.CloneSourceResponse;

import java.util.List;

public interface CloneService {
    void clone(PostCloneRequest postCloneRequest);

    List<CloneSourceResponse> getCloneSourceList(Long memberId);

    void deleteCloneSource(Long cloneSourceId);
}
