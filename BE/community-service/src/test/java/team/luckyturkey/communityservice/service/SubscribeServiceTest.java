package team.luckyturkey.communityservice.service;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import team.luckyturkey.communityservice.entity.Subscribe;
import team.luckyturkey.communityservice.repository.SubscribeRepository;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class SubscribeServiceTest {

    @Autowired
    SubscribeService subscribeService;
    @Autowired
    SubscribeRepository subscribeRepository;
    @Autowired
    EntityManager em;

    @Test
    @Rollback(value = false)
    public void subscribe() {
        // given
        Subscribe s = Subscribe.builder().
                memberId(5678L)
                .followingMemberId(1234L)
                .subscribeDate(new Date())
                .build();

        // when
        Subscribe result = subscribeService.subscribe(s.getMemberId(), s.getFollowingMemberId());

        // then
        assertEquals(s.getMemberId(), result.getMemberId());
    }

    @Test
    @Rollback()
    public void existsByMemberIdAndFollowingMemberId() {
        // given
        Subscribe s = Subscribe.builder().
                memberId(5678L)
                .followingMemberId(1234L)
                .subscribeDate(new Date())
                .build();
        // when
        boolean result = subscribeRepository.existsByMemberIdAndFollowingMemberId(s.getMemberId(), s.getFollowingMemberId());

        // then
        assertTrue(result);
    }

    @Test
    public void getSubscribersCount() throws Exception {
        // given
        Long selectedMemberId = 1234L;

        // when
        int result = subscribeService.getSubscribersCount(selectedMemberId);

        // then
        assertEquals(1, result);
    }

    @Test
    public void unsubscribe() throws Exception {
        // given
        Long memberId = 5678L;
        Long selectedMemberId = 1234L;

        // when
        subscribeService.unsubscribe(memberId, selectedMemberId);

        // then
        assertFalse(subscribeRepository.existsByMemberIdAndFollowingMemberId(memberId, selectedMemberId));
    }
}
