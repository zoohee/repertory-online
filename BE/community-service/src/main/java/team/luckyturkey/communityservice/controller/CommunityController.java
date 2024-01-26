package team.luckyturkey.communityservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import team.luckyturkey.communityservice.service.SubscribeService;

@RestController
@RequiredArgsConstructor
public class CommunityController {

    private final SubscribeService subscribeService;

    @GetMapping("/test")
    public String test() {
        return "community test!";
    }

    @PostMapping("/subscibe")
    public void subscribe(@RequestBody Long selectedMemberId) {
        // TODO: Request Header jwt에서 memberId 받아 오기
        Long memberId = 1L;
        subscribeService.subscribe(memberId, selectedMemberId);
    }

    @GetMapping("/subscribers")
    public int getSubscribers() {
        int subscribersCount = 0;
        return subscribersCount;
    }
}
