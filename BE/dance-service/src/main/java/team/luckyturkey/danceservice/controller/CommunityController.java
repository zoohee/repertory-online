package team.luckyturkey.danceservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.luckyturkey.danceservice.controller.responsedto.CommunityFeedResponse;
import team.luckyturkey.danceservice.domain.FeedType;
import team.luckyturkey.danceservice.service.FeedFacadeService;

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
}