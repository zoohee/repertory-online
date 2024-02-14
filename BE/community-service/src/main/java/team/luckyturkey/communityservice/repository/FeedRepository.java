package team.luckyturkey.communityservice.repository;

import java.time.LocalDateTime;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import team.luckyturkey.communityservice.dto.MemberDto;
import team.luckyturkey.communityservice.entity.Feed;

import java.util.List;

@Repository
public interface FeedRepository extends JpaRepository<Feed, Long> {

    Feed getFeedById(Long id);
    Feed getFeedByOriginId(Long originId);

    @Query("SELECT f FROM Feed f WHERE f.feedDisable = false AND f.memberId IN :followingList")
    List<Feed> findFeedsByFollowingList(List<Long> followingList, Pageable pageable);

    @Query("SELECT f FROM Feed f WHERE f.feedDisable = false ORDER BY f.likeCount DESC, f.downloadCount DESC")
    List<Feed> findFeedsAll(Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE Feed f SET f.feedDisable = :feedDisable WHERE f.id = :feedId")
    void updateFeedDisableById(Long feedId, boolean feedDisable);

    @Modifying
    @Query("UPDATE Feed f SET f.downloadCount = f.downloadCount + 1 WHERE f.id = :feedId")
    int incrementDownloadCount(Long feedId);

    @Query("SELECT f FROM Feed f WHERE f.memberId IN :members")
    List<Feed> findFeedsByMembers(List<Long> members);

    @Query("SELECT f FROM Feed f WHERE f.memberId = :memberId and f.feedDisable = true")
    List<Feed> findPublicFeedsByMemberId(Long memberId);

    List<Feed> findFeedsByMemberId(Long memberId);

    @Query("SELECT l.likeActive FROM LikeLog l WHERE l.memberId = :memberId AND l.feedId = :feedId ORDER BY timestamp DESC")
    List<Boolean> findIsLike(Long memberId, Long feedId, Pageable pageable);

    Feed findFeedByOriginId(Long originId);
}
