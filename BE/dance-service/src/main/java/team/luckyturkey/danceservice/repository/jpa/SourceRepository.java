package team.luckyturkey.danceservice.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import team.luckyturkey.danceservice.entity.Source;

public interface SourceRepository extends JpaRepository<Source, Long> {
}
