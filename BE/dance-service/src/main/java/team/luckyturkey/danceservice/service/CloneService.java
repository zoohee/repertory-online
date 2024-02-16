package team.luckyturkey.danceservice.service;

import team.luckyturkey.danceservice.controller.requestdto.PostCloneRequest;
import team.luckyturkey.danceservice.controller.responsedto.StandardSourceResponse;

public interface CloneService {
    StandardSourceResponse clone(PostCloneRequest postCloneRequest);
}
