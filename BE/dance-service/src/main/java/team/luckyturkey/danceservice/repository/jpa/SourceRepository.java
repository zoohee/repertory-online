package team.luckyturkey.danceservice.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import team.luckyturkey.danceservice.entity.Source;

import java.util.List;
import java.util.Optional;

public interface SourceRepository extends JpaRepository<Source, Long> {

    @Query("select s from Source s join fetch s.sourceDetail where s.memberId = :memberId")
    List<Source> findByMemberId(Long memberId);

    @Query("select s from Source s join fetch s.sourceDetail where s.sourceDetail.sourceName like %:keyword%")
    List<Source> findBySourceNameLike(String keyword);

    @Query("select s from Source s join fetch s.sourceDetail")
    Optional<Source> findByIdWithDetail(Long sourceId);
}