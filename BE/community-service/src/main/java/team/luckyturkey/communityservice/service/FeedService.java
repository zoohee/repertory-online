package team.luckyturkey.communityservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.luckyturkey.communityservice.client.DanceServiceClient;
import team.luckyturkey.communityservice.client.MemberServiceClient;
import team.luckyturkey.communityservice.dto.MemberDto;
import team.luckyturkey.communityservice.dto.OriginDto;
import team.luckyturkey.communityservice.dto.request.SourceCloneRequest;
import team.luckyturkey.communityservice.dto.response.FeedDetailResponse;
import team.luckyturkey.communityservice.entity.Feed;
import team.luckyturkey.communityservice.entity.FeedType;
import team.luckyturkey.communityservice.entity.LikeLog;
import team.luckyturkey.communityservice.exception.NullException;
import team.luckyturkey.communityservice.repository.FeedLikeCacheRepository;
import team.luckyturkey.communityservice.repository.FeedRepository;
import team.luckyturkey.communityservice.repository.SubscribeRepository;
import team.luckyturkey.communityservice.util.ErrorCode;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class FeedService {

    private final FeedRepository feedRepository;
    private final DanceServiceClient danceServiceClient;
    private final MemberServiceClient memberServiceClient;

    @Transactional
    public Feed insertFeed(Feed feed) {
        feed.setDownloadCount(0L);
        feed.setLikeCount(0L);
        log.info(feed.toString());
        return feedRepository.save(feed);
    }

    // 구독한 사람들의 피드를 최신순으로 불러 오는 함수
    public List<Feed> getFeeds(List<Long> followingList, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<Feed> feeds = feedRepository.findFeedsByFollowingList(followingList, pageable);
        return feedRepository.findFeedsByFollowingList(followingList, pageable);
    }

    // 피드의 상세 정보를 불러 오는 함수
    public Feed getFeedDetail(Long feedId) {
        Feed feed = feedRepository.getFeedById(feedId);
        return feedRepository.getFeedById(feedId);
    }

    // dance server에서 소스 or 레퍼토리 정보 가져오는 함수
    public OriginDto getOriginDto(Long originId, FeedType feedType) {
        OriginDto originDto = danceServiceClient.getOriginDetail(originId, feedType);
        if (originDto == null) {
            throw new NullException(ErrorCode.NOT_EXISTS_ERROR);
        }
        return originDto;
    }

    public List<FeedDetailResponse> getFeedsAndDetail(List<Feed> feeds) {
        List<FeedDetailResponse> feedDetailResponseList = new ArrayList<>();
        for (Feed feed : feeds) {
            OriginDto originDto = danceServiceClient.getOriginDetail(feed.getOriginId(), feed.getFeedType());
            MemberDto memberDto = memberServiceClient.getMemberInfo(feed.getMemberId());
            FeedDetailResponse feedDetailResponse = FeedDetailResponse.builder()
                    .feedId(feed.getId())
                    .feedType(feed.getFeedType())
                    .likeCount(feed.getLikeCount())
                    .downloadCount(feed.getDownloadCount())
                    .feedDisable(feed.getFeedDisable())
                    .originId(feed.getOriginId())
                    .memberId(originDto.getMemberId())
                    .feedName(originDto.getFeedName())
                    .feedUrl(originDto.getFeedUrl())
                    .feedThumbnailUrl(originDto.getFeedThumbnailUrl())
                    .feedDate(originDto.getFeedDate())
                    .memberName(memberDto.getMemberName())
                    .memberProfile(memberDto.getMemberProfile())
                    .build();
            feedDetailResponseList.add(feedDetailResponse);
        }
        return feedDetailResponseList;
    }

    public List<Feed> getAllFeeds(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return feedRepository.findFeedsAll(pageable);
    }

    public void setFeedPublic(Feed feed, boolean feedDisable) {
        Long originId = feed.getOriginId();
        feed = feedRepository.getFeedByOriginId(originId);
        feedRepository.updateFeedDisableById(feed.getId(), feedDisable);
    }

    public void cloneSource(Long feedId, Long memberId) {
        Feed feed = feedRepository.getFeedById(feedId);
        Long originId = feed.getOriginId();

        feedRepository.incrementDownloadCount(feedId);
        SourceCloneRequest sourceCloneRequest = SourceCloneRequest.builder()
                .originId(originId)
                .memberId(memberId)
                .build();
        danceServiceClient.cloneSource(sourceCloneRequest);
    }
}
