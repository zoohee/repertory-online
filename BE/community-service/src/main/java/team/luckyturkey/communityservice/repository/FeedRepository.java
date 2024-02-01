package team.luckyturkey.communityservice.repository;

import java.time.LocalDateTime;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import team.luckyturkey.communityservice.entity.Feed;

import java.util.List;

public interface FeedRepository extends JpaRepository<Feed, Long> {

    Feed getFeedById(Long id);
    @Query("SELECT f FROM Feed f WHERE f.memberId IN :followingList")
    List<Feed> findFeedsByFollowingList(List<Long> followingList, Pageable pageable);
    @Query("SELECT f FROM Feed f ORDER BY f.likeCount DESC, f.downloadCount DESC")
    List<Feed> findFeedsAll(Pageable pageable);

}
