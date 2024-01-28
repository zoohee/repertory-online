package team.luckyturkey.communityservice.repository;

import java.util.List;
import java.util.Map;

public interface LikeCacheRepository {
    Long increaseLike(Long sourceId);
    Long decreaseLike(Long sourceId);

    Long findBySourceId(Long sourceId);

    Map<Long, Long> findBySourceIdList(List<Long> sourceIdList);
}