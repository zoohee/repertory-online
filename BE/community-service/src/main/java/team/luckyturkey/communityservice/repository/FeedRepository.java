package team.luckyturkey.communityservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.luckyturkey.communityservice.entity.Feed;
import team.luckyturkey.communityservice.entity.Subscribe;

import java.util.List;

public interface FeedRepository extends JpaRepository<Feed, Long> {

    Feed getFeedById(Long id);
}
