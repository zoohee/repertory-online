package team.luckyturkey.communityservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.luckyturkey.communityservice.entity.Subscribe;
import team.luckyturkey.communityservice.repository.SubscribeRepository;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;

    public int getSubscribersCount(Long followingMemberId) {
        int subscribersCount = subscribeRepository.countByFollowingMemberId(followingMemberId);
        if (subscribersCount < 0) {
            throw new IllegalStateException("Invalid subscriber count");
        }
        return subscribersCount;
    }

    @Transactional
    public Subscribe subscribe(Long memberId, Long selectedMemberId) {
        if (memberId == null || selectedMemberId == null) {
            throw new IllegalArgumentException("MemberId and selectedMemberId cannot be null");
        }

        if (subscribeRepository.existsByMemberIdAndFollowingMemberId(memberId, selectedMemberId)) {
            throw new IllegalStateException("Subscription already exists");
        }
        
        Subscribe subscribe = Subscribe.builder().
                memberId(memberId)
                .followingMemberId(selectedMemberId)
                .subscribeDate(new Date())
                .build();

        Subscribe s = subscribeRepository.save(subscribe);
        return s;
    }

    @Transactional
    public void unsubscribe(Long memberId, Long selectedMemberId) {

        if (subscribeRepository.existsByMemberIdAndFollowingMemberId(memberId, selectedMemberId)) {
            subscribeRepository.deleteByMemberIdAndFollowingMemberId(memberId, selectedMemberId);
        } else {
            throw new IllegalStateException("ID must exist");
        }
    }

    public List<Long> getFollowingList(Long memberId) {
        return subscribeRepository.findFollowingListByMemberId(memberId);
    }
}
