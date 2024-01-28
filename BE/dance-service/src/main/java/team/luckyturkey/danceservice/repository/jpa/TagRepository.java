package team.luckyturkey.danceservice.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import team.luckyturkey.danceservice.entity.Tag;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findByMemberId(Long userId);
    boolean existsTagByTagNameAndMemberId(String tagName, Long memberId);
    Tag findByTagNameAndMemberId(String tagName, Long memberId);
}
