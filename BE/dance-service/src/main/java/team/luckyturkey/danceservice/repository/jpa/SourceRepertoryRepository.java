package team.luckyturkey.danceservice.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import team.luckyturkey.danceservice.domain.entity.mapper.SourceRepertory;

public interface SourceRepertoryRepository extends JpaRepository<SourceRepertory, Long> {
    void deleteByIdRepertoryId(Long repertoryId);
}
