package team.luckyturkey.danceservice.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import team.luckyturkey.danceservice.domain.entity.Source;
import team.luckyturkey.danceservice.domain.entity.SourceTag;
import team.luckyturkey.danceservice.domain.entity.id.SourceTagPK;

import java.util.List;

public interface SourceTagRepository extends JpaRepository<SourceTag, Long> {
    @Query("select ts.source from SourceTag ts join fetch ts.source.sourceDetail where ts.tag.id = :tagId")
    List<Source> findSourceListByTagId(Long tagId);

    @Query("select ts from SourceTag ts join fetch ts.tag where ts.source.id = :sourceId and ts.tag.memberId = :memberId")
    List<SourceTag> findTagListByMemberIdAndSourceId(Long sourceId, Long memberId);

    void deleteById(SourceTagPK id);
}
