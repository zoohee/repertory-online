package team.luckyturkey.danceservice.service;

import org.springframework.web.multipart.MultipartFile;
import team.luckyturkey.danceservice.controller.requestdto.PatchSourceRequest;
import team.luckyturkey.danceservice.controller.requestdto.PostSourceRequest;
import team.luckyturkey.danceservice.controller.responsedto.StandardSourceResponse;

import java.util.List;

public interface SourceService {
    List<StandardSourceResponse> getMySourceList(Long memberId);

    List<StandardSourceResponse> searchSource(String keyword);

    StandardSourceResponse getSource(Long sourceId);

    StandardSourceResponse saveSource(PostSourceRequest postSourceRequest, MultipartFile sourceVideo, Long userId);

    Long updateSource(Long sourceId, PatchSourceRequest patchSourceRequest, Long memberId);


    void deleteSource(Long sourceId);
}
