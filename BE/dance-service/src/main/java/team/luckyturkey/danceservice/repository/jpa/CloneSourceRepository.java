package team.luckyturkey.danceservice.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import team.luckyturkey.danceservice.domain.entity.CloneSource;

 public interface CloneSourceRepository extends JpaRepository<CloneSource, Long> {
}
