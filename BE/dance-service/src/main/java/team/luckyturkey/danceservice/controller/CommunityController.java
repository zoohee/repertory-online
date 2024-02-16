package team.luckyturkey.danceservice.controller;

import feign.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.luckyturkey.danceservice.controller.responsedto.CommunityFeedResponse;
import team.luckyturkey.danceservice.domain.FeedType;
import team.luckyturkey.danceservice.service.FeedFacadeService;

import java.util.List;

@RestController
@RequestMapping("/detail")
@RequiredArgsConstructor
public class CommunityController {

    private final FeedFacadeService feedFacadeService;

    @GetMapping("/{originId}/{feedType}")
    public ResponseEntity<CommunityFeedResponse> getFeed(
            @PathVariable Long originId,
            @PathVariable FeedType feedType
    ){
        CommunityFeedResponse response = feedFacadeService.findFeed(originId, feedType);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/source/{keyword}")
    public ResponseEntity<List<CommunityFeedResponse>> searchSource(
            @PathVariable String keyword
    ) {
        List<CommunityFeedResponse> response = feedFacadeService.searchSourceWithKeyword(keyword);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/repertory/{keyword}")
    public ResponseEntity<List<CommunityFeedResponse>> searchRepertory(
            @PathVariable String keyword
    ){
        List<CommunityFeedResponse> response = feedFacadeService.searchRepertoryWithKeyword(keyword);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/repertory/source/{originId}")
    public ResponseEntity<List<CommunityFeedResponse>> getSourceList(@PathVariable Long originId) {
        List<CommunityFeedResponse> response = feedFacadeService.getSourceList(originId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/download/{originId}/{memberId}")
    public ResponseEntity<Boolean> getIsDownloaded(@PathVariable Long originId,
                                                   @PathVariable Long memberId) {
        Boolean response = feedFacadeService.getIsDownloaded(originId, memberId);
        return ResponseEntity.ok(response);
    }
}