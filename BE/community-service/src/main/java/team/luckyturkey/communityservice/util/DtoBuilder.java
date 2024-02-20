package team.luckyturkey.communityservice.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import team.luckyturkey.communityservice.client.DanceServiceClient;
import team.luckyturkey.communityservice.dto.MemberDto;
import team.luckyturkey.communityservice.dto.OriginDto;
import team.luckyturkey.communityservice.dto.response.FeedResponse;
import team.luckyturkey.communityservice.dto.response.FeedDetailResponse;
import team.luckyturkey.communityservice.dto.response.ProfileSubscriberResponse;
import team.luckyturkey.communityservice.entity.Feed;
import team.luckyturkey.communityservice.entity.LikeLog;
import team.luckyturkey.communityservice.repository.FeedLikeCacheRepository;
import team.luckyturkey.communityservice.repository.FeedRepository;
import team.luckyturkey.communityservice.service.SubscribeService;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DtoBuilder {

    private final FeedLikeCacheRepository feedLikeCacheRepository;
    private final FeedRepository feedRepository;
    private final DanceServiceClient danceServiceClient;
    private final SubscribeService subscribeService;

    public FeedResponse mapFeedResponse(OriginDto s, Feed feed, MemberDto memberDto, Long memberId) {
        Pageable pageable = PageRequest.of(0, 1);
        List<Boolean> isLikedList = feedRepository.findIsLike(memberId, feed.getId(), pageable);
        Boolean isLiked = false;
        if (!isLikedList.isEmpty()) {
            isLiked = isLikedList.get(0); // 리스트가 비어있지 않은 경우에 첫 번째 요소 가져오기
        }
        Boolean isDownloaded = danceServiceClient.getIsDownloaded(s.getOriginId(), memberId);


        return FeedResponse.builder()
                .feedId(feed.getId())
                .feedType(feed.getFeedType())
                .likeCount(feedLikeCacheRepository.findByFeedId(feed.getId()))
                .downloadCount(feed.getDownloadCount())
                .feedDisable(feed.getFeedDisable())
                .isLiked(isLiked)
                .isDownloaded(isDownloaded)
                .originId(s.getOriginId())
                .memberId(s.getMemberId())
                .feedName(s.getFeedName())
                .feedUrl(s.getFeedUrl())
                .feedThumbnailUrl(s.getFeedThumbnailUrl())
                .feedDate(s.getFeedDate())
                .memberName(memberDto.getMemberName())
                .memberProfile(memberDto.getMemberProfile())
                .build();
    }

    public ProfileSubscriberResponse mapProfileSubscriberResponse(Long memberId,
                                                                  FeedResponse feedResponse,
                                                                  MemberDto memberDto) {
        return ProfileSubscriberResponse.builder()
                .memberId(feedResponse.getMemberId())
                .memberName(memberDto.getMemberName())
                .followerCount(subscribeService.getSubscribersCount(memberDto.getMemberId()))
                .isFollowed(subscribeService.getIsFollowed(memberId, memberDto.getMemberId()))
                .memberProfile(memberDto.getMemberProfile())
                .build();
    }

    public FeedDetailResponse mapFeedDetailResponse(FeedResponse feedResponse,
                                              ProfileSubscriberResponse profileSubscriberResponse,
                                              List<FeedResponse> sources) {

        return FeedDetailResponse.builder()
                .feed(feedResponse)
                .profile(profileSubscriberResponse)
                .sources(sources)
                .build();
    }

    public LikeLog buildLikeLog(Long memberId, Long feedId, Boolean likeActive) {
        return LikeLog.builder()
                .memberId(memberId)
                .feedId(feedId)
                .likeActive(likeActive)
                .timestamp(new Date())
                .build();
    }

    public ProfileSubscriberResponse mapProfileSubscriberResponseById(Long myId, Long memberId, MemberDto memberDto) {
        return ProfileSubscriberResponse.builder()
                .memberId(memberId)
                .memberName(memberDto.getMemberName())
                .isFollowed(subscribeService.getIsFollowed(myId, memberId))
                .memberProfile(memberDto.getMemberProfile())
                .build();
    }


}
