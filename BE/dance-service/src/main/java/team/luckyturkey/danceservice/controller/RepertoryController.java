package team.luckyturkey.danceservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import team.luckyturkey.danceservice.controller.requestdto.PatchRepertoryStatusRequest;
import team.luckyturkey.danceservice.controller.requestdto.PostRepertoryRequest;
import team.luckyturkey.danceservice.controller.responsedto.StandardRepertoryResponse;
import team.luckyturkey.danceservice.service.RepertoryService;
import team.luckyturkey.danceservice.util.JwtUtil;

import java.util.List;

@RestController
@RequestMapping("/repertory")
@RequiredArgsConstructor
public class RepertoryController {

    private final RepertoryService repertoryService;
    private final JwtUtil jwtUtil;

//    //todo: should get memberId from token :done
//    @Value("${test.environment.memberId}")
//    private Long TEST_MEMBER_ID;

    @PostMapping
    public ResponseEntity<StandardRepertoryResponse> postRepertory(
            @RequestPart PostRepertoryRequest postRepertoryRequest,
            @RequestPart MultipartFile repertoryVideo,
            @RequestPart MultipartFile repertoryThumbnail,
            @RequestHeader("Authorization") final String accessToken
    ) {

        //todo: should get memberId from token :done

        String atc = accessToken.split(" ")[1];

        Long memberId = jwtUtil.getMemberId(atc);

        StandardRepertoryResponse response = repertoryService.saveRepertory(postRepertoryRequest, repertoryVideo, repertoryThumbnail, memberId);
        return ResponseEntity.ok(response);
    }

    @Operation(description = "요청한 사용자가 가진 repertory 배열을 반환합니다")
    @GetMapping("/mine")
    public ResponseEntity<List<StandardRepertoryResponse>> getRepertoryList(
            @RequestHeader("Authorization") final String accessToken
    ){
        //todo: should get memberId from token :done
        String atc = accessToken.split(" ")[1];

        Long memberId = jwtUtil.getMemberId(atc);

        List<StandardRepertoryResponse> response = repertoryService.getRepertoryList(memberId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<StandardRepertoryResponse>> searchRepertoryWithName(
            @RequestParam String keyword
    ){
        List<StandardRepertoryResponse> response = repertoryService.searchRepertory(keyword);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{repertoryId}/video")
    public ResponseEntity<StandardRepertoryResponse> patchRepertoryVideo(
            @PathVariable Long repertoryId,
            MultipartFile repertoryVideo
    ){
        StandardRepertoryResponse response = repertoryService.modifyRepertoryVideo(repertoryId, repertoryVideo);
        return ResponseEntity.ok(response);
    }

    @Operation(description = "레퍼토리의 공개 여부를 수정합니다")
    @PatchMapping("/{repertoryId}/status")
    public ResponseEntity<Long> patchRepertoryStatus(
            @PathVariable Long repertoryId,
            @RequestBody PatchRepertoryStatusRequest patchRepertoryStatusRequest
    ){
        Long patchedRepertoryId = repertoryService.modifyRepertoryStatus(repertoryId, patchRepertoryStatusRequest);
        return ResponseEntity.ok(patchedRepertoryId);
    }

    @DeleteMapping("/{repertoryId}")
    public ResponseEntity<Long> deleteRepertory(
            @PathVariable Long repertoryId
    ){
        Long deletedRepertoryId = repertoryService.deleteRepertory(repertoryId);
        return ResponseEntity.ok(deletedRepertoryId);
    }
}
