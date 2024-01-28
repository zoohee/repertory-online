package team.luckyturkey.communityservice.controller;

import java.util.Map;
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

    @PostMapping("/subscribe")
    public void subscribe(@RequestBody Map<String, Long> data) {
        // TODO: Request Header jwt에서 memberId 받아 오기
        Long memberId = 5678L;
        Long selectedMemberId = data.get("selectedMemberId");

        subscribeService.subscribe(memberId, selectedMemberId);
    }

    @GetMapping("/subscribers")
    public int getSubscribers() {
        // TODO: Request Header jwt에서 memberId 받아 오기
        Long memberId = 1234L;
        return subscribeService.getSubscribers(memberId);
    }
}
