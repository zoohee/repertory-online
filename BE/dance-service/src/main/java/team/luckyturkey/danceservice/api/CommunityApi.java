package team.luckyturkey.danceservice.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import team.luckyturkey.danceservice.configuration.OpenFeignConfig;
import team.luckyturkey.danceservice.domain.FeedType;

@FeignClient(value = "community", url = "${api.community.url}", configuration = OpenFeignConfig.class)
public interface CommunityApi {

    @PostMapping(value = "/feed", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> postFeed(@RequestParam("media-id") Long originId, @RequestParam("feed-type") FeedType feedType);

    @PatchMapping(value = "/feed", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> disableFeed();

}