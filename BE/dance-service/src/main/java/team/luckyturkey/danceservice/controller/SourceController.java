package team.luckyturkey.danceservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import team.luckyturkey.danceservice.controller.requestdto.PatchSourceRequest;
import team.luckyturkey.danceservice.controller.requestdto.PatchSourceStatusRequest;
import team.luckyturkey.danceservice.controller.requestdto.PostSourceRequest;
import team.luckyturkey.danceservice.controller.responsedto.StandardSourceResponse;
import team.luckyturkey.danceservice.service.SourceService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/source")
public class SourceController {

    // TODO: jwt token
    @Value("${test.environment.memberId}")
    private Long TEST_MEMBER_ID;


    private final SourceService sourceService;

    @GetMapping
    public ResponseEntity<List<StandardSourceResponse>> searchSourceList(
            @Param("keyword") String keyword
    ){
        List<StandardSourceResponse> responseList = sourceService.searchSource(keyword);
        return ResponseEntity.ok(responseList);
    }

    @PostMapping
    public ResponseEntity<StandardSourceResponse> postSource(
            @RequestPart(name = "postSource") PostSourceRequest postSourceRequest,
            @RequestPart MultipartFile sourceVideo,
            @RequestPart MultipartFile sourceThumbnail
    ){
        StandardSourceResponse response = sourceService.saveSource(postSourceRequest, sourceVideo, sourceThumbnail, TEST_MEMBER_ID);
        try {
            return ResponseEntity.created(new URI(response.getSourceUrl())).body(response);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{sourceId}")
    public ResponseEntity<StandardSourceResponse> getSourceById(
            @PathVariable Long sourceId
    ){
        StandardSourceResponse response = sourceService.getSource(sourceId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{sourceId}")
    public ResponseEntity<Long> patchSource(
            @PathVariable Long sourceId,
            @RequestBody PatchSourceRequest patchSourceRequest
    ){
        Long updatedSource = sourceService.updateSource(sourceId, patchSourceRequest, TEST_MEMBER_ID);
        return ResponseEntity.ok().body(updatedSource);
    }

    @PatchMapping("/{sourceId}/status")
    public ResponseEntity<Long> patchSourceStatus(
            @PathVariable Long sourceId,
            @RequestBody PatchSourceStatusRequest patchSourceStatusRequest
    ){
        Long memberId = TEST_MEMBER_ID;
        Long patchedSourceId = sourceService.updateSourceStatus(sourceId, patchSourceStatusRequest, memberId);

        return ResponseEntity.ok(patchedSourceId);
    }

    @DeleteMapping("/{sourceId}")
    public ResponseEntity<Long> deleteSource(
            @PathVariable Long sourceId
    ){
        sourceService.deleteSource(sourceId);
        return ResponseEntity.ok(sourceId);
    }

    @GetMapping("/mine")
    public ResponseEntity<List<StandardSourceResponse>> getMySourceList(
    ){
        List<StandardSourceResponse> responseList = sourceService.getMySourceList(TEST_MEMBER_ID);
        return ResponseEntity.ok(responseList);
    }

}