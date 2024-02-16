package team.luckyturkey.danceservice.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import team.luckyturkey.danceservice.domain.entity.Tag;

import java.util.List;
import java.util.Set;

public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findByMemberId(Long memberId);

    @Query("select t.tagName from Tag t where t.memberId = :memberId")
    Set<String> findTagNameByMemberId(Long memberId);
    boolean existsTagByTagNameAndMemberId(String tagName, Long memberId);
    Tag findByTagNameAndMemberId(String tagName, Long memberId);

}
