package team.luckyturkey.communityservice.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import team.luckyturkey.communityservice.client.MemberServiceClient;
import team.luckyturkey.communityservice.dto.MemberDto;
import team.luckyturkey.communityservice.dto.OriginDto;
import team.luckyturkey.communityservice.dto.response.FeedDetailResponse;
import team.luckyturkey.communityservice.entity.Feed;
import team.luckyturkey.communityservice.entity.LikeLog;
import team.luckyturkey.communityservice.service.FeedService;
import team.luckyturkey.communityservice.service.LikeService;
import team.luckyturkey.communityservice.service.SubscribeService;
import team.luckyturkey.communityservice.util.DtoBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/feed")
public class FeedController {

    private final SubscribeService subscribeService;
    private final FeedService feedService;
    private final LikeService likeService;
    private final MemberServiceClient memberServiceClient;
    private final DtoBuilder dtoBuilder;

    @PostMapping
    public void insertFeed(@RequestBody Feed feed) {
        // TODO: Request Header jwt에서 memberId 받아 오기
        Long memberId = 1234L;

        feed.setMemberId(memberId);
        feed.setLikeCount(0L);
        feed.setDownloadCount(0L);

        feedService.insertFeed(feed);
    }

    @GetMapping("/subscribe/{page}/{pageSize}")
    public List<FeedDetailResponse> getUserSubscribeFeedList(@PathVariable("page") int page,
                                                    @PathVariable("pageSize") int pageSize) {
        // TODO: Request Header jwt에서 memberId 받아 오기
        Long memberId = 5678L;
        List<Long> followingList = subscribeService.getFollowingList(memberId);
        List<Feed> feeds = feedService.getFeeds(followingList, page, pageSize);

        return feedService.getFeedsAndDetail(feeds, memberId);
    }

    @GetMapping("/{page}/{pageSize}")
    public List<FeedDetailResponse> getUserFeedList(@PathVariable("page") int page,
                                                    @PathVariable("pageSize") int pageSize) {
        // TODO: Request Header jwt에서 memberId 받아 오기
        Long memberId = 5678L;

        List<Feed> feeds = feedService.getAllFeeds(page, pageSize);
        for (Feed feed : feeds) {
            feed.setLikeCount(likeService.getFeedLikeCount(feed.getId()));
        }

        return feedService.getFeedsAndDetail(feeds, memberId);
    }

    @GetMapping("/detail/{feedId}")
    public FeedDetailResponse getFeedDetail(@PathVariable("feedId") Long feedId) {
        // TODO: Request Header jwt에서 memberId 받아 오기
        Long memberId = 5678L;

        Feed feed = feedService.getFeedDetail(feedId);
        Long originId = feed.getOriginId();
        OriginDto originDto = feedService.getOriginDto(originId, feed.getFeedType());
        Long likeCount = likeService.getFeedLikeCount(feedId);
        MemberDto memberDto = memberServiceClient.getMemberInfo(feed.getMemberId());

        return dtoBuilder.mapFeedDetailResponse(originDto, feed, memberDto, memberId);
    }

    @PatchMapping("/{feedId}/like")
    public Long likeSource(@PathVariable("feedId") Long feedId) {
        // TODO: Request Header jwt에서 memberId 받아 오기
        Long memberId = 5678L;

        LikeLog likeLog = LikeLog.builder()
                .memberId(memberId)
                .feedId(feedId)
                .likeActive(true)
                .timestamp(new Date())
                .build();

        // TODO: DB 저장은 비동기 처리
        likeService.insertLikeLog(likeLog);
        return likeService.insertLikeCache(feedId);
    }

    @PatchMapping("/{feedId}/notlike")
    public Long cancelLikeSource(@PathVariable("feedId") Long feedId) {
        // TODO: Request Header jwt에서 memberId 받아 오기
        Long memberId = 5678L;

        LikeLog likeLog = LikeLog.builder()
                .memberId(memberId)
                .feedId(feedId)
                .likeActive(false)
                .timestamp(new Date())
                .build();

        // TODO: DB 저장은 비동기 처리
        likeService.insertLikeLog(likeLog);
        return likeService.cancelLikeCache(feedId);
    }

    @PostMapping("/disable")
    public void setFeedPublic(@RequestBody Feed feed) {
        feedService.setFeedPublic(feed, true);
    }

    @PatchMapping("/disable")
    public void setFeedPrivate(@RequestBody Feed feed) {
        feedService.setFeedPublic(feed, false);
    }

    @GetMapping("/profile/{memberId}")
    public List<FeedDetailResponse> getMyProfile(@PathVariable Long memberId) {
        // TODO: Request Header jwt에서 memberId 받아 오기
        Long myId = 5678L;

        if (myId.equals(memberId)) {
            return feedService.getFeedsByMyId(myId);
        } else {
            return feedService.getFeedsByMemberId(memberId);
        }
    }

}
