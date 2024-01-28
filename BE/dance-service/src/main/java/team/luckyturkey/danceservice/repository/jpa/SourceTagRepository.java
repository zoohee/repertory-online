package team.luckyturkey.danceservice.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import team.luckyturkey.danceservice.entity.Source;
import team.luckyturkey.danceservice.entity.SourceTag;

import java.util.List;

public interface SourceTagRepository extends JpaRepository<SourceTag, Long> {
    @Query("select ts.source from SourceTag ts where ts.tag.id = :tagId")
    List<Source> findSourceListByTagId(@Param("tagId") Long tagId);

}
