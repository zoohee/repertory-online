package team.luckyturkey.communityservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.luckyturkey.communityservice.entity.Subscribe;
import team.luckyturkey.communityservice.entity.SubscribePK;
import team.luckyturkey.communityservice.repository.SubscribeRepository;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;

    public int getSubscribersCount(Long followingMemberId) {
        int subscribersCount = subscribeRepository.countByIdFollowingMemberId(followingMemberId);
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

        if (subscribeRepository.existsByIdIdAndIdFollowingMemberId(memberId, selectedMemberId)) {
            throw new IllegalStateException("Subscription already exists");
        }

        SubscribePK subscribePK = new SubscribePK();
        subscribePK.setId(memberId);
        subscribePK.setFollowingMemberId(selectedMemberId);
        Subscribe subscribe = Subscribe.builder()
                .id(subscribePK)
                .subscribeDate(new Date())
                .build();

        Subscribe s = subscribeRepository.save(subscribe);
        return s;
    }

    @Transactional
    public void unsubscribe(Long memberId, Long selectedMemberId) {

        if (subscribeRepository.existsByIdIdAndIdFollowingMemberId(memberId, selectedMemberId)) {
            subscribeRepository.deleteByIdIdAndIdFollowingMemberId(memberId, selectedMemberId);
        } else {
            throw new IllegalStateException("ID must exist");
        }
    }

    public List<Long> getFollowingList(Long memberId) {
        // SubscribePK 객체 생성
        SubscribePK subscribePK = new SubscribePK();
        subscribePK.setId(memberId); // 예시로 5678L 값을 사용, 실제로는 원하는 값으로 설정

        System.out.println(subscribeRepository.findFollowingListByIdId(memberId));
        return subscribeRepository.findFollowingListByIdId(memberId);
    }
}
