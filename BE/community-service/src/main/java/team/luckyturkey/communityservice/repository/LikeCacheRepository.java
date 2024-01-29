package team.luckyturkey.communityservice.repository;

import java.util.List;
import java.util.Map;

public interface LikeCacheRepository {
    Long increaseLike(Long feedId);
    Long decreaseLike(Long feedId);
    Long findByFeedId(Long feedId);

    Map<Long, Long> findBySourceIdList(List<Long> sourceIdList);
}