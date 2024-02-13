package team.luckyturkey.communityservice.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import team.luckyturkey.communityservice.dto.MemberDto;
import team.luckyturkey.communityservice.dto.OriginDto;
import team.luckyturkey.communityservice.dto.response.FeedDetailResponse;
import team.luckyturkey.communityservice.entity.Feed;
import team.luckyturkey.communityservice.entity.FeedType;
import team.luckyturkey.communityservice.repository.FeedLikeCacheRepository;
import team.luckyturkey.communityservice.repository.FeedRepository;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class DtoBuilder {

    private final FeedLikeCacheRepository feedLikeCacheRepository;
    private final FeedRepository feedRepository;

    public FeedDetailResponse mapFeedDetailResponse(OriginDto s, Feed feed, MemberDto memberDto, Long memberId) {
        Optional<Boolean> isLikedOptional = Optional.ofNullable(feedRepository.findIsLike(memberId, feed.getId()));

        return FeedDetailResponse.builder()
                .feedId(feed.getId())
                .feedType(FeedType.SOURCE)
                .likeCount(feedLikeCacheRepository.findByFeedId(feed.getId()))
                .downloadCount(feed.getDownloadCount())
                .feedDisable(feed.getFeedDisable())
                .isLiked(isLikedOptional.orElse(false))
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
}
