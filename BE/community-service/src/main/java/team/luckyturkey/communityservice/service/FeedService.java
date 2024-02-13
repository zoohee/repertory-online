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
import team.luckyturkey.communityservice.exception.NotExistsException;
import team.luckyturkey.communityservice.exception.NullException;
import team.luckyturkey.communityservice.repository.FeedLikeCacheRepository;
import team.luckyturkey.communityservice.repository.FeedRepository;
import team.luckyturkey.communityservice.repository.SubscribeRepository;
import team.luckyturkey.communityservice.util.DtoBuilder;
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
    private final FeedLikeCacheRepository feedLikeCacheRepository;
    private final DtoBuilder dtoBuilder;

    @Transactional
    public void insertFeed(Feed feed) {
        feed.setDownloadCount(0L);
        feed.setLikeCount(0L);
        feedRepository.save(feed);
        feedLikeCacheRepository.setLikeCountToZero(feed.getId());
    }

    // 구독한 사람들의 피드를 최신순으로 불러 오는 함수
    public List<Feed> getFeeds(List<Long> followingList, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        return feedRepository.findFeedsByFollowingList(followingList, pageable);
    }

    // 피드의 상세 정보를 불러 오는 함수
    public Feed getFeed(Long feedId) {
        Feed feed = feedRepository.getFeedById(feedId);
        return feedRepository.getFeedById(feedId);
    }

    public FeedDetailResponse getFeedDetail(Feed feed, Long memberId) {
        Long originId = feed.getOriginId();
        OriginDto originDto = getOriginDto(originId, feed.getFeedType());
        MemberDto memberDto = memberServiceClient.getMemberInfo(feed.getMemberId());
        return dtoBuilder.mapFeedDetailResponse(originDto, feed, memberDto, memberId);
    }

    // dance server에서 소스 or 레퍼토리 정보 가져오는 함수
    public OriginDto getOriginDto(Long originId, FeedType feedType) {
        OriginDto originDto = danceServiceClient.getOriginDetail(originId, feedType);
        if (originDto == null) {
            throw new NullException(ErrorCode.NOT_EXISTS_ERROR);
        }
        return originDto;
    }

    public List<FeedDetailResponse> getFeedsAndDetail(List<Feed> feeds, Long memberId) {
        List<FeedDetailResponse> feedDetailResponseList = new ArrayList<>();
        for (Feed feed : feeds) {
            OriginDto originDto = danceServiceClient.getOriginDetail(feed.getOriginId(), feed.getFeedType());
            if (originDto.getFeedName() == null) { continue; }
            MemberDto memberDto = memberServiceClient.getMemberInfo(feed.getMemberId());
//            if (memberDto.getMemberId() == null) { continue; }

            FeedDetailResponse feedDetailResponse = dtoBuilder.mapFeedDetailResponse(originDto, feed, memberDto, memberId);
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
        if (feed == null) {
           throw new NotExistsException(ErrorCode.NOT_EXISTS_ERROR);
        }
        Long originId = feed.getOriginId();

        feedRepository.incrementDownloadCount(feedId);
        SourceCloneRequest sourceCloneRequest = SourceCloneRequest.builder()
                .originId(originId)
                .memberId(memberId)
                .build();
        danceServiceClient.cloneSource(sourceCloneRequest);
    }

    // 멤버 아이디로 피드 리스트 불러오는 함수
    public List<FeedDetailResponse> getFeedsByMemberId(Long memberId) {
        List<Feed> feeds = feedRepository.findPublicFeedsByMemberId(memberId);
        return getFeedsAndDetail(feeds, memberId);
    }

    public List<FeedDetailResponse> getFeedsByMyId(Long myId) {
        List<Feed> feeds = feedRepository.findFeedsByMemberId(myId);
        return getFeedsAndDetail(feeds, myId);
    }
}
