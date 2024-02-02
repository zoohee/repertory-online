package team.luckyturkey.communityservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import team.luckyturkey.communityservice.dto.response.SubscriberResponse;
import java.util.List;

@FeignClient(name = "member")
public interface MemberServiceClient {
    @GetMapping("/member/following")
    List<SubscriberResponse> getFollowingDetailList(@RequestParam List<Long> followingList);
}
