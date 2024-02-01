package team.luckyturkey.communityservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import team.luckyturkey.communityservice.dto.OriginDto;
import team.luckyturkey.communityservice.entity.FeedType;

@FeignClient(name = "dance")
public interface DanceServiceClient {

    @GetMapping("/dance/detail/{originId}/{feedType}")
    OriginDto getOriginDetail(@PathVariable("originId") Long originId,
                              @PathVariable("feedType") FeedType feedType);
}
