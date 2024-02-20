package team.luckyturkey.communityservice.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import team.luckyturkey.communityservice.client.DanceServiceClient;
import team.luckyturkey.communityservice.client.MemberServiceClient;
import team.luckyturkey.communityservice.dto.MemberDto;
import team.luckyturkey.communityservice.dto.OriginDto;
import team.luckyturkey.communityservice.dto.response.FeedDetailResponse;
import team.luckyturkey.communityservice.dto.response.ProfileSubscriberResponse;
import team.luckyturkey.communityservice.dto.response.FeedRepertoryDetailResponse;
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
    private final DanceServiceClient danceServiceClient;
    private final DtoBuilder dtoBuilder;
    private final JwtUtil jwtUtil;

    @PostMapping
    public void insertFeed(@RequestBody Feed feed, @RequestHeader("Authorization") final String accessToken) {
        Long memberId = jwtUtil.extractMemberIdFromToken(accessToken);
        feed.setMemberId(memberId);

        feedService.insertFeed(feed);
    }

    @GetMapping("/subscribe/{page}/{pageSize}")
    public List<FeedDetailResponse> getUserSubscribeFeedList(
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
    public List<FeedDetailResponse> getUserFeedList(
            @PathVariable("page") int page,
            @PathVariable("pageSize") int pageSize,
            @RequestHeader("Authorization") final String accessToken
    ) {
        Long memberId = jwtUtil.extractMemberIdFromToken(accessToken);

        List<Feed> feeds = feedService.getAllFeeds(page, pageSize);

        return feedService.getFeedsAndDetail(feeds, memberId);
    }

    @GetMapping("/detail/{feedId}")
    public FeedRepertoryDetailResponse getFeedDetail(
            @PathVariable("feedId") Long feedId,
            @RequestHeader("Authorization") final String accessToken) {
        Long memberId = jwtUtil.extractMemberIdFromToken(accessToken);

        Feed feed = feedService.getFeed(feedId);

        FeedDetailResponse feedDetailResponse = feedService.getFeedDetail(feed, memberId);
        MemberDto memberDto = memberServiceClient.getMemberInfo(feedDetailResponse.getMemberId());
        ProfileSubscriberResponse profileSubscriberResponse = ProfileSubscriberResponse.builder()
                .memberId(feedDetailResponse.getMemberId())
                .memberName(memberDto.getMemberName())
                .followerCount(subscribeService.getSubscribersCount(feed.getMemberId()))
                .isFollowed(subscribeService.getIsFollowed(memberId, memberDto.getMemberId()))
                .memberProfile(memberDto.getMemberProfile())
                .build();

        if (feed.getFeedType() == FeedType.REPERTORY) {
            List<OriginDto> originDtoList = danceServiceClient.getSourceList(feed.getOriginId());
            List<FeedDetailResponse> sources = new ArrayList<>();
            for (OriginDto o : originDtoList) {
                Feed sourceFeed = feedService.getFeedByOriginId(o.getOriginId());
                FeedDetailResponse s = dtoBuilder.mapFeedDetailResponse(o, sourceFeed, memberDto, memberId);
                sources.add(s);
            }

            // repertory 기준
            return FeedRepertoryDetailResponse.builder()
                    .feed(feedDetailResponse)
                    .profile(profileSubscriberResponse)
                    .sources(sources)
                    .build();
        }

        return FeedRepertoryDetailResponse.builder()
                .feed(feedDetailResponse)
                .profile(profileSubscriberResponse)
                .build();
    }

    @PatchMapping("/{feedId}/like")
    public Long likeSource(
            @PathVariable("feedId") Long feedId,
            @RequestHeader("Authorization") final String accessToken
    ) {
        Long memberId = jwtUtil.extractMemberIdFromToken(accessToken);

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
    public Long cancelLikeSource(
            @PathVariable("feedId") Long feedId,
            @RequestHeader("Authorization") final String accessToken
    ) {
        Long memberId = jwtUtil.extractMemberIdFromToken(accessToken);

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
        feedService.setFeedPublic(feed, false);
    }

    @PatchMapping("/disable")
    public void setFeedPrivate(@RequestBody Feed feed) {
        feedService.setFeedPublic(feed, true);
    }

    @GetMapping("/profile/{memberId}")
    public List<FeedDetailResponse> getProfileFeed(@PathVariable Long memberId, @RequestHeader("Authorization") final String accessToken) {
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

        return ProfileSubscriberResponse.builder()
                .memberId(memberId)
                .memberName(memberDto.getMemberName())
                .isFollowed(subscribeService.getIsFollowed(myId, memberId))
                .followerCount(subscribeService.getSubscribersCount(memberId))
                .memberProfile(memberDto.getMemberProfile())
                .build();
    }

}
