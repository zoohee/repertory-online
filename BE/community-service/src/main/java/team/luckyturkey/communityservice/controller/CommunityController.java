package team.luckyturkey.communityservice.controller;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import team.luckyturkey.communityservice.dto.response.SubscriberResponse;
import team.luckyturkey.communityservice.service.*;

@RestController
@RequiredArgsConstructor
public class CommunityController {

    private final SubscribeService subscribeService;
    private final TokenService tokenService;
    private final FeedService feedService;
    private final JwtUtil jwtUtil;

    @GetMapping("/test")
    public String test() {
        return "community test!";
    }

    @PostMapping("/subscribe")
    public void subscribe(@RequestBody Map<String, Long> data, @RequestHeader("Authorization") final String accessToken) {
        // TODO: Request Header jwt에서 memberId 받아 오기 :done

        String atc = accessToken.split(" ")[1];
        tokenService.setToken(atc);
        Long memberId = jwtUtil.getMemberId(atc);
        Long selectedMemberId = data.get("selectedMemberId");

        subscribeService.subscribe(memberId, selectedMemberId);
    }

    @GetMapping("/subscribers")
    public int getSubscribersCount( @RequestHeader("Authorization") final String accessToken ) {
        // TODO: Request Header jwt에서 memberId 받아 오기 :done
        String atc = accessToken.split(" ")[1];
        tokenService.setToken(atc);
        Long memberId = jwtUtil.getMemberId(atc);
        return subscribeService.getSubscribersCount(memberId);
    }

    @DeleteMapping("/subscribe")
    public void unsubscribe(@RequestBody Map<String, Long> data, @RequestHeader("Authorization") final String accessToken) {
        // TODO: Request Header jwt에서 memberId 받아 오기 :done
        String atc = accessToken.split(" ")[1];
        tokenService.setToken(atc);
        Long memberId = jwtUtil.getMemberId(atc);
        Long selectedMemberId = data.get("selectedMemberId");

        subscribeService.unsubscribe(memberId, selectedMemberId);
    }

    @GetMapping("/subscribe/list")
    public List<SubscriberResponse> getFollowingList(@RequestHeader("Authorization") final String accessToken) {
        // TODO: Request Header jwt에서 memberId 받아 오기 :done
        String atc = accessToken.split(" ")[1];
        tokenService.setToken(atc);
        Long memberId = jwtUtil.getMemberId(atc);

        List<Long> followingList = subscribeService.getFollowingList(memberId);
        return subscribeService.getFollowingDetailList(followingList);
    }

    @PostMapping("/source/{feedId}/clone")
    public void cloneSource(@PathVariable("feedId") Long feedId, @RequestHeader("Authorization") final String accessToken) {
        // TODO: Request Header jwt에서 memberId 받아 오기 :done
        String atc = accessToken.split(" ")[1];
        tokenService.setToken(atc);
        Long memberId = jwtUtil.getMemberId(atc);

        feedService.cloneSource(feedId, memberId);
    }

}
