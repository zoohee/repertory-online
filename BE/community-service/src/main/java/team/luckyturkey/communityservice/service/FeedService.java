package team.luckyturkey.communityservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.luckyturkey.communityservice.entity.Feed;
import team.luckyturkey.communityservice.entity.LikeLog;
import team.luckyturkey.communityservice.repository.FeedRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class FeedService {

    private final FeedRepository feedRepository;

    @Transactional
    public Feed insertFeed(Feed feed) {
        feed.setDownloadCount(0L);
        feed.setLikeCount(0L);
        log.info(feed.toString());
        return feedRepository.save(feed);
    }

}
