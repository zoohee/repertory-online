package team.luckyturkey.communityservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.luckyturkey.communityservice.client.DanceServiceClient;
import team.luckyturkey.communityservice.dto.OriginDto;
import team.luckyturkey.communityservice.dto.request.SourceCloneRequest;
import team.luckyturkey.communityservice.dto.response.FeedDetailResponse;
import team.luckyturkey.communityservice.entity.Feed;
import team.luckyturkey.communityservice.entity.FeedType;
import team.luckyturkey.communityservice.entity.LikeLog;
import team.luckyturkey.communityservice.repository.FeedRepository;
import team.luckyturkey.communityservice.repository.SubscribeRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class FeedService {

    private final FeedRepository feedRepository;
    private final DanceServiceClient danceServiceClient;

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
        return feedRepository.findFeedsByFollowingList(followingList, pageable);
    }

    // 피드의 상세 정보를 불러 오는 함수
    public Feed getFeedDetail(Long feedId) {
        return feedRepository.getFeedById(feedId);
    }

    // dance server에서 소스 or 레퍼토리 정보 가져오는 함수
    public OriginDto getOriginDto(Long originId, FeedType feedType) {
        return danceServiceClient.getOriginDetail(originId, feedType);
    }

    public List<FeedDetailResponse> getFeedsAndDetail(List<Feed> feeds) {
        List<FeedDetailResponse> feedDetailResponseList = new ArrayList<>();
        for (Feed feed : feeds) {
            OriginDto originDto = danceServiceClient.getOriginDetail(feed.getOriginId(), feed.getFeedType());
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
                    .feedDate(originDto.getFeedDate())
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
