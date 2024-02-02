package team.luckyturkey.danceservice.service;

import team.luckyturkey.danceservice.controller.requestdto.PostCloneRequest;

public interface CloneService {
    void clone(PostCloneRequest postCloneRequest);
}
