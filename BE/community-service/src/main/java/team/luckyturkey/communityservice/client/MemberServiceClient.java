package team.luckyturkey.communityservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import team.luckyturkey.communityservice.dto.MemberDto;
import team.luckyturkey.communityservice.dto.response.SubscriberResponse;
import java.util.List;

@FeignClient(name = "member", url = "${api.member.url}")
public interface MemberServiceClient {
    @GetMapping("/member/following")
    List<SubscriberResponse> getFollowingDetailList(@RequestParam List<Long> followingList);
    @GetMapping("/member/memberinfo")
    MemberDto getMemberInfo(@RequestBody Long memberId);
}
