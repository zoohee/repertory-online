package team.luckyturkey.communityservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.luckyturkey.communityservice.entity.Subscribe;
import team.luckyturkey.communityservice.repository.SubscribeRepository;

import java.util.Date;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;

    public int getSubscribers(Long followingMemberId) {
        return subscribeRepository.countByFollowingMemberId(followingMemberId);
    }

    @Transactional
    public Subscribe subscribe(Long memberId, Long selectedMemberId) {
        if (memberId == null || selectedMemberId == null) {
            throw new IllegalArgumentException("MemberId and selectedMemberId cannot be null");
        }

        if (subscribeRepository.existsByMemberIdAndFollowingMemberId(memberId, selectedMemberId)) {
            // 예외 처리 또는 적절한 응답 반환
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
}
