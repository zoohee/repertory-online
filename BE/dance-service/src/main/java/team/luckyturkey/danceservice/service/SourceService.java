package team.luckyturkey.danceservice.service;

import org.springframework.web.multipart.MultipartFile;
import team.luckyturkey.danceservice.controller.requestdto.PatchSourceRequest;
import team.luckyturkey.danceservice.controller.requestdto.PostSourceRequest;
import team.luckyturkey.danceservice.entity.Source;

import java.util.List;

public interface SourceService {
    List<Source> getMySourceList(Long memberId);

    List<Source> searchSource(String keyword);

    Source getSource(Long sourceId);

    Source saveSource(PostSourceRequest postSourceRequest, MultipartFile sourceVideo, Long userId);

    Long updateSource(Long sourceId, PatchSourceRequest patchSourceRequest);

    void deleteSource(Long sourceId);
}
