package team.luckyturkey.communityservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import team.luckyturkey.communityservice.client.MemberServiceClient;
import team.luckyturkey.communityservice.dto.response.SubscriberResponse;
import team.luckyturkey.communityservice.entity.Subscribe;
import team.luckyturkey.communityservice.entity.SubscribePK;
import team.luckyturkey.communityservice.exception.AlreadySubscribedException;
import team.luckyturkey.communityservice.exception.InvalidDataException;
import team.luckyturkey.communityservice.exception.NullException;
import team.luckyturkey.communityservice.repository.SubscribeRepository;
import team.luckyturkey.communityservice.util.ErrorCode;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;
    private final MemberServiceClient memberServiceClient;

    public int getSubscribersCount(Long followingMemberId) {
        int subscribersCount = subscribeRepository.countByIdFollowingMemberId(followingMemberId);
        if (subscribersCount < 0) {
            throw new InvalidDataException(ErrorCode.INTER_SERVER_ERROR);
        }
        return subscribersCount;
    }

    @Transactional
    public Subscribe subscribe(Long memberId, Long selectedMemberId) {
        if (memberId == null || selectedMemberId == null) {
            throw new NullException(ErrorCode.DUPLICATE_DATA);
        }

        if (subscribeRepository.existsByIdIdAndIdFollowingMemberId(memberId, selectedMemberId)) {
            throw new AlreadySubscribedException(ErrorCode.DUPLICATE_DATA);
        }

        SubscribePK subscribePK = SubscribePK.builder()
                .id(memberId)
                .followingMemberId(selectedMemberId)
                .build();

        Subscribe subscribe = Subscribe.builder()
                .id(subscribePK)
                .subscribeDate(new Date())
                .build();

        return subscribeRepository.save(subscribe);
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
        SubscribePK subscribePK = SubscribePK.builder().id(memberId).build();
        return subscribeRepository.findFollowingListByIdId(memberId);
    }

    public List<SubscriberResponse> getFollowingDetailList(List<Long> followingList) {
        return memberServiceClient.getFollowingDetailList(followingList);
    }
}
