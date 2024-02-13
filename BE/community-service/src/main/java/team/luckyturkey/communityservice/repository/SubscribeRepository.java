package team.luckyturkey.communityservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import team.luckyturkey.communityservice.entity.Subscribe;
import team.luckyturkey.communityservice.entity.SubscribePK;

import java.util.List;

public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {

    int countByIdFollowingMemberId(Long followingMemberId);
    boolean existsByIdIdAndIdFollowingMemberId(Long memberId, Long selectedMemberId);
    void deleteByIdIdAndIdFollowingMemberId(Long memberId, Long selectedMemberId);
    @Query("SELECT s.id.followingMemberId FROM Subscribe s WHERE s.id.id = :memberId")
    List<Long> findFollowingListByIdId(Long memberId);
}
