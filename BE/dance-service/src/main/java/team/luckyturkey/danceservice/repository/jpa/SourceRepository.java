package team.luckyturkey.danceservice.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import team.luckyturkey.danceservice.domain.entity.Source;

import java.util.List;
import java.util.Optional;

public interface SourceRepository extends JpaRepository<Source, Long> {

    @Query("select s from Source s join fetch s.sourceDetail where s.memberId = :memberId")
    List<Source> findByMemberId(Long memberId);

    @Query("select s from Source s join fetch s.sourceDetail where s.sourceDetail.sourceName like %:keyword%")
    List<Source> findBySourceNameLike(String keyword);

    @Query("select s from Source s join fetch s.sourceDetail where s.id = :sourceId")
    Optional<Source> findByIdWithDetail(Long sourceId);

    @Query("select s from Source s " +
            "join fetch s.sourceDetail " +
            "join fetch s.sourceTagList " +
            "where s.id = :sourceId")
    Optional<Source> findByIdWithDetailAndTag(Long sourceId);


    @Query("select s from Source s where s.id in :sourceIdList")
    List<Source> findByIdIn(List<Long> sourceIdList);
}