package team.luckyturkey.danceservice.service;

import org.springframework.web.multipart.MultipartFile;
import team.luckyturkey.danceservice.controller.requestdto.PatchRepertoryStatusRequest;
import team.luckyturkey.danceservice.controller.requestdto.PostRepertoryRequest;
import team.luckyturkey.danceservice.controller.responsedto.StandardRepertoryResponse;

import java.util.List;

public interface RepertoryService {
    StandardRepertoryResponse saveRepertory(PostRepertoryRequest postRepertoryRequest, MultipartFile repertoryVideo, MultipartFile repertoryThumbnailVideo, Long memberId);
    List<StandardRepertoryResponse> getRepertoryList(Long memberId);
    Long deleteRepertory(Long repertoryId);
    StandardRepertoryResponse modifyRepertoryVideo(Long repertoryId, MultipartFile repertoryVideo);

    Long modifyRepertoryStatus(Long repertoryId, PatchRepertoryStatusRequest patchRepertoryStatusRequest);

    List<StandardRepertoryResponse> searchByName(String keyword);
}
