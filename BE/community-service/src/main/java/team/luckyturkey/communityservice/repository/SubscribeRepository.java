package team.luckyturkey.communityservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import team.luckyturkey.communityservice.entity.Subscribe;

import java.util.List;

public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {

    int countByFollowingMemberId(Long followingMemberId);
    boolean existsByMemberIdAndFollowingMemberId(Long memberId, Long selectedMemberId);
    void deleteByMemberIdAndFollowingMemberId(Long memberId, Long selectedMemberId);
    @Query("SELECT s.followingMemberId FROM Subscribe s WHERE s.memberId = :memberId")
    List<Long> findFollowingListByMemberId(Long memberId);

}
