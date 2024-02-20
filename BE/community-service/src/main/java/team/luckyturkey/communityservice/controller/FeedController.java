package team.luckyturkey.communityservice.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import team.luckyturkey.communityservice.client.MemberServiceClient;
import team.luckyturkey.communityservice.dto.MemberDto;
import team.luckyturkey.communityservice.dto.response.FeedResponse;
import team.luckyturkey.communityservice.dto.response.ProfileSubscriberResponse;
import team.luckyturkey.communityservice.dto.response.FeedDetailResponse;
import team.luckyturkey.communityservice.entity.Feed;
import team.luckyturkey.communityservice.entity.FeedType;
import team.luckyturkey.communityservice.entity.LikeLog;
import team.luckyturkey.communityservice.service.*;
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
    private final JwtUtil jwtUtil;

    @PostMapping
    public void insertFeed(@RequestBody Feed feed, @RequestHeader("Authorization") final String accessToken) {
        Long memberId = jwtUtil.extractMemberIdFromToken(accessToken);
        feed.setMemberId(memberId);

        feedService.insertFeed(feed);
    }

    @GetMapping("/subscribe/{page}/{pageSize}")
    public List<FeedResponse> getUserSubscribeFeedList(
            @PathVariable("page") int page,
            @PathVariable("pageSize") int pageSize,
            @RequestHeader("Authorization") final String accessToken
    ) {
        Long memberId = jwtUtil.extractMemberIdFromToken(accessToken);
        List<Long> followingList = subscribeService.getFollowingList(memberId);
        List<Feed> feeds = feedService.getFollowingFeeds(followingList, page, pageSize);

        return feedService.getFeedsAndDetail(feeds, memberId);
    }

    @GetMapping("/{page}/{pageSize}")
    public List<FeedResponse> getUserFeedList(
            @PathVariable("page") int page,
            @PathVariable("pageSize") int pageSize,
            @RequestHeader("Authorization") final String accessToken
    ) {
        Long memberId = jwtUtil.extractMemberIdFromToken(accessToken);

        List<Feed> feeds = feedService.getAllFeeds(page, pageSize);

        return feedService.getFeedsAndDetail(feeds, memberId);
    }

    @GetMapping("/detail/{feedId}")
    public FeedDetailResponse getFeedDetail(
            @PathVariable("feedId") Long feedId,
            @RequestHeader("Authorization") final String accessToken) {
        Long memberId = jwtUtil.extractMemberIdFromToken(accessToken);

        Feed feed = feedService.getFeed(feedId);

        FeedResponse feedResponse = feedService.getFeedDetail(feed, memberId);
        MemberDto memberDto = memberServiceClient.getMemberInfo(feedResponse.getMemberId());
        ProfileSubscriberResponse profileSubscriberResponse = dtoBuilder.mapProfileSubscriberResponse(memberId, feedResponse, memberDto);

        List<FeedResponse> sources = new ArrayList<>();
        if (feed.getFeedType() == FeedType.REPERTORY) {
             sources = feedService.getSources(memberId, feed, memberDto);
        }

        return dtoBuilder.mapFeedDetailResponse(feedResponse, profileSubscriberResponse, sources);
    }

    @PatchMapping("/{feedId}/like")
    public Long likeSource(
            @PathVariable("feedId") Long feedId,
            @RequestHeader("Authorization") final String accessToken
    ) {
        Long memberId = jwtUtil.extractMemberIdFromToken(accessToken);

        LikeLog likeLog = dtoBuilder.buildLikeLog(memberId, feedId, true);

        // TODO: DB 저장은 비동기 처리
        likeService.insertLikeLog(likeLog);
        return likeService.insertLikeCache(feedId);
    }

    @PatchMapping("/{feedId}/notlike")
    public Long cancelLikeSource(
            @PathVariable("feedId") Long feedId,
            @RequestHeader("Authorization") final String accessToken
    ) {
        Long memberId = jwtUtil.extractMemberIdFromToken(accessToken);

        LikeLog likeLog = dtoBuilder.buildLikeLog(memberId, feedId, false);

        // TODO: DB 저장은 비동기 처리
        likeService.insertLikeLog(likeLog);
        return likeService.cancelLikeCache(feedId);
    }

    @PostMapping("/disable")
    public void setFeedPublic(@RequestBody Feed feed) {
        feedService.setFeedPublic(feed, false);
    }

    @PatchMapping("/disable")
    public void setFeedPrivate(@RequestBody Feed feed) {
        feedService.setFeedPublic(feed, true);
    }

    @GetMapping("/profile/{memberId}")
    public List<FeedResponse> getProfileFeed(@PathVariable Long memberId, @RequestHeader("Authorization") final String accessToken) {
        Long myId = jwtUtil.extractMemberIdFromToken(accessToken);

        if (myId.equals(memberId)) {
            return feedService.getFeedsByMyId(myId);
        } else {
            return feedService.getFeedsByMemberId(memberId);
        }
    }

    @GetMapping("/profile/subscriber/{memberId}")
    public ProfileSubscriberResponse getProfileSubscriber(
            @PathVariable Long memberId,
            @RequestHeader("Authorization") final String accessToken
    ) {
        Long myId = jwtUtil.extractMemberIdFromToken(accessToken);
        MemberDto memberDto = memberServiceClient.getMemberInfo(memberId);

        return dtoBuilder.mapProfileSubscriberResponseById(myId, memberId, memberDto);
    }

}
