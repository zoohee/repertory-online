package team.luckyturkey.communityservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.luckyturkey.communityservice.entity.LikeLog;

public interface LikeLogRepository extends JpaRepository<LikeLog, Long> {

}
