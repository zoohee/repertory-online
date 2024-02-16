package team.luckyturkey.danceservice.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import team.luckyturkey.danceservice.domain.entity.CloneSource;

import java.util.List;

public interface CloneSourceRepository extends JpaRepository<CloneSource, Long> {

 @Query("select cs from CloneSource cs join fetch cs.cloneSourceDetail where cs.memberId = :memberId")
 List<CloneSource> findByMemberId(Long memberId);

 @Query("select count(cs) from CloneSource cs where cs.source.id = :originId and cs.memberId = :memberId")
 int countByOriginIdAndMemberId(Long originId, Long memberId);

}
