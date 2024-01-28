package team.luckyturkey.communityservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.luckyturkey.communityservice.entity.Subscribe;

public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {

    int countByFollowingMemberId(Long followingMemberId);
    boolean existsByMemberIdAndFollowingMemberId(Long memberId, Long selectedMemberId);
    void deleteByMemberIdAndFollowingMemberId(Long memberId, Long selectedMemberId);
}
