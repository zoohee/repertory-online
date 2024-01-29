package team.luckyturkey.communityservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.luckyturkey.communityservice.entity.LikeLog;
import team.luckyturkey.communityservice.repository.FeedLikeCacheRepository;
import team.luckyturkey.communityservice.repository.LikeCacheRepository;
import team.luckyturkey.communityservice.repository.LikeLogRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LikeService {

    private final LikeLogRepository likeLogRepository;
    private final FeedLikeCacheRepository feedLikeCacheRepository;

    @Transactional
    public Long insertLikeCache(Long feedId) {
        return feedLikeCacheRepository.increaseLike(feedId);
    }

    @Transactional
    public LikeLog insertLikeLog(LikeLog likeLog) {
        return likeLogRepository.save(likeLog);
    }
}
