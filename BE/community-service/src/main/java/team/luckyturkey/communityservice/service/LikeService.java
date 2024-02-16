package team.luckyturkey.communityservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.luckyturkey.communityservice.entity.LikeLog;
import team.luckyturkey.communityservice.exception.InvalidDataException;
import team.luckyturkey.communityservice.repository.FeedLikeCacheRepository;
import team.luckyturkey.communityservice.repository.LikeLogRepository;
import team.luckyturkey.communityservice.util.ErrorCode;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class LikeService {

    private final LikeLogRepository likeLogRepository;
    private final FeedLikeCacheRepository feedLikeCacheRepository;

    @Transactional
    public Long insertLikeCache(Long feedId) {
        return feedLikeCacheRepository.increaseLike(feedId);
    }

    @Transactional
    public void insertLikeLog(LikeLog likeLog) {
        likeLogRepository.save(likeLog);
    }

    @Transactional
    public Long cancelLikeCache(Long feedId) {
        Long likeCount = feedLikeCacheRepository.decreaseLike(feedId);
        if (likeCount < 0) {
            feedLikeCacheRepository.increaseLike(feedId);
            throw new InvalidDataException(ErrorCode.INTER_SERVER_ERROR);
        }
        return likeCount;
    }

    public Long getFeedLikeCount(Long feedId) {
        return feedLikeCacheRepository.findByFeedId(feedId);
    }
}
