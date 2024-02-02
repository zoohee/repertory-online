package team.luckyturkey.communityservice.controller;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.luckyturkey.communityservice.dto.OriginDto;
import team.luckyturkey.communityservice.dto.response.FeedDetailResponse;
import team.luckyturkey.communityservice.entity.Feed;
import team.luckyturkey.communityservice.service.FeedService;
import team.luckyturkey.communityservice.service.SubscribeService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/feed")
public class FeedController {

    private final SubscribeService subscribeService;
    private final FeedService feedService;

    @GetMapping("/subscribe/{page}/{pageSize}")
    public List<FeedDetailResponse> getUserSubscribeFeedList(@PathVariable("page") int page,
                                                    @PathVariable("pageSize") int pageSize) {
        // TODO: Request Header jwt에서 memberId 받아 오기
        Long memberId = 5678L;
        List<Long> followingList = subscribeService.getFollowingList(memberId);
        List<Feed> feeds = feedService.getFeeds(followingList, page, pageSize);
        // TODO: Need DanceServiceClient Test
        return feedService.getFeedsAndDetail(feeds);
    }

    @GetMapping("/{page}/{pageSize}")
    public List<FeedDetailResponse> getUserFeedList(@PathVariable("page") int page,
                                                    @PathVariable("pageSize") int pageSize) {
        List<Feed> feeds = feedService.getAllFeeds(page, pageSize);
        return feedService.getFeedsAndDetail(feeds);
    }

    @PostMapping("/")
    public void insertFeed(@RequestBody Feed feed) {
        // TODO: Request Header jwt에서 memberId 받아 오기
        Long memberId = 1234L;

        feed.setMemberId(memberId);
        feed.setLikeCount(0L);
        feed.setDownloadCount(0L);

        feedService.insertFeed(feed);
    }

    @GetMapping("/feed/{feedId}/detail")
    public FeedDetailResponse getFeedDetail(@PathVariable("feedId") Long feedId) {
        // TODO: Need DanceServiceClient Test
        Feed feed = feedService.getFeedDetail(feedId);
        Long originId = feed.getOriginId();
        OriginDto originDto = feedService.getOriginDto(originId, feed.getFeedType());

        return FeedDetailResponse.builder()
                .feedId(feedId)
                .feedType(feed.getFeedType())
                .likeCount(feed.getLikeCount())
                .downloadCount(feed.getDownloadCount())
                .feedDisable(feed.getFeedDisable())
                .originId(originId)
                .memberId(originDto.getMemberId())
                .feedName(originDto.getFeedName())
                .feedUrl(originDto.getFeedUrl())
                .feedDate(originDto.getFeedDate())
                .build();
    }

    @PostMapping("/disable")
    public void setFeedPublic(@RequestBody Feed feed) {
        feedService.setFeedPublic(feed, true);
    }

    @PatchMapping("/disable")
    public void setFeedPrivate(@RequestBody Feed feed) {
        feedService.setFeedPublic(feed, false);
    }
}
