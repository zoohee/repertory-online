package team.luckyturkey.communityservice.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import team.luckyturkey.communityservice.entity.Feed;

import java.util.List;

public interface FeedRepository extends JpaRepository<Feed, Long> {

    Feed getFeedById(Long id);
    @Query("SELECT f FROM Feed f WHERE f.memberId IN :followingList")
    List<Feed> findPostsByFollowingList(List<Long> followingList, Pageable pageable);

}
