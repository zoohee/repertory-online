package team.luckyturkey.communityservice.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import team.luckyturkey.communityservice.dto.MemberDto;
import team.luckyturkey.communityservice.dto.OriginDto;
import team.luckyturkey.communityservice.dto.response.FeedDetailResponse;
import team.luckyturkey.communityservice.entity.Feed;
import team.luckyturkey.communityservice.entity.FeedType;
import team.luckyturkey.communityservice.repository.FeedLikeCacheRepository;
import team.luckyturkey.communityservice.repository.FeedRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class DtoBuilder {

    private final FeedLikeCacheRepository feedLikeCacheRepository;
    private final FeedRepository feedRepository;

    public FeedDetailResponse mapFeedDetailResponse(OriginDto s, Feed feed, MemberDto memberDto, Long memberId) {
        Pageable pageable = PageRequest.of(0, 1);
        List<Boolean> isLikedList = feedRepository.findIsLike(memberId, feed.getId(), pageable);
        Boolean isLiked = false;
        if (!isLikedList.isEmpty()) {
            isLiked = isLikedList.get(0); // 리스트가 비어있지 않은 경우에 첫 번째 요소 가져오기
        }

        return FeedDetailResponse.builder()
                .feedId(feed.getId())
                .feedType(FeedType.SOURCE)
                .likeCount(feedLikeCacheRepository.findByFeedId(feed.getId()))
                .downloadCount(feed.getDownloadCount())
                .feedDisable(feed.getFeedDisable())
                .isLiked(isLiked)
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
