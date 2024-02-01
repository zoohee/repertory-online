package team.luckyturkey.communityservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.luckyturkey.communityservice.client.DanceServiceClient;
import team.luckyturkey.communityservice.dto.OriginDto;
import team.luckyturkey.communityservice.entity.Feed;
import team.luckyturkey.communityservice.entity.FeedType;
import team.luckyturkey.communityservice.entity.LikeLog;
import team.luckyturkey.communityservice.repository.FeedRepository;

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

//    public List<Feed> getFeeds()

    public Feed getFeedDetail(Long feedId) {
        return feedRepository.getFeedById(feedId);
    }

    public OriginDto getOriginDto(Long originId, FeedType feedType) {
        return danceServiceClient.getOriginDetail(originId, feedType);
    }
}
