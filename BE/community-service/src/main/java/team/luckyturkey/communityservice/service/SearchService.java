package team.luckyturkey.communityservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.luckyturkey.communityservice.client.DanceServiceClient;
import team.luckyturkey.communityservice.client.MemberServiceClient;
import team.luckyturkey.communityservice.dto.MemberDto;
import team.luckyturkey.communityservice.dto.OriginDto;
import team.luckyturkey.communityservice.dto.response.FeedDetailResponse;
import team.luckyturkey.communityservice.entity.Feed;
import team.luckyturkey.communityservice.entity.FeedType;
import team.luckyturkey.communityservice.repository.FeedLikeCacheRepository;
import team.luckyturkey.communityservice.repository.FeedRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class SearchService {
    private final DanceServiceClient danceServiceClient;
    private final FeedRepository feedRepository;
    private final FeedLikeCacheRepository feedLikeCacheRepository;
    private final MemberServiceClient memberServiceClient;

    public List<FeedDetailResponse> searchSourceByName(String keyword) {
        List<OriginDto> sources = danceServiceClient.searchSource(keyword);
        List<FeedDetailResponse> feeds = new ArrayList<>();

        for (OriginDto s : sources) {
            Feed feed = feedRepository.getFeedByOriginId(s.getOriginId());
            MemberDto memberDto = memberServiceClient.getMemberInfo(feed.getMemberId());

            // TODO: dance service query -> only public (disable=true)
            FeedDetailResponse feedDetailResponse = FeedDetailResponse.builder()
                    .feedId(feed.getId())
                    .feedType(FeedType.SOURCE)
                    .likeCount(feedLikeCacheRepository.findByFeedId(feed.getId()))
                    .downloadCount(feed.getDownloadCount())
                    .feedDisable(feed.getFeedDisable())
                    .originId(s.getOriginId())
                    .memberId(s.getMemberId())
                    .feedName(s.getFeedName())
                    .feedUrl(s.getFeedUrl())
                    .feedThumbnailUrl(s.getFeedThumbnailUrl())
                    .feedDate(s.getFeedDate())
                    .memberName(memberDto.getMemberName())
                    .memberProfile(memberDto.getMemberProfile())
                    .build();

            feeds.add(feedDetailResponse);
        }
        return feeds;
    }
}
