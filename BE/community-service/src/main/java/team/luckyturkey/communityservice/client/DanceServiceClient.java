package team.luckyturkey.communityservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import team.luckyturkey.communityservice.dto.OriginDto;
import team.luckyturkey.communityservice.dto.request.SourceCloneRequest;
import team.luckyturkey.communityservice.entity.FeedType;

@FeignClient(name="dance", url = "${api.dance.url}")
public interface DanceServiceClient {

    @GetMapping("/detail/{originId}/{feedType}")
    OriginDto getOriginDetail(@PathVariable("originId") Long originId,
                              @PathVariable("feedType") FeedType feedType);

    @PostMapping("/clone")
    void cloneSource(@RequestBody SourceCloneRequest sourceCloneRequest);
}
