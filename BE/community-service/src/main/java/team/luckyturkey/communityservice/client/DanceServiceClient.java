package team.luckyturkey.communityservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import team.luckyturkey.communityservice.config.OpenFeignConfig;
import team.luckyturkey.communityservice.dto.OriginDto;
import team.luckyturkey.communityservice.dto.request.SourceCloneRequest;
import team.luckyturkey.communityservice.entity.FeedType;

import java.util.List;

@FeignClient(name="dance", url = "${api.dance.url}", configuration = OpenFeignConfig.class)
public interface DanceServiceClient {

    @GetMapping("/detail/{originId}/{feedType}")
    OriginDto getOriginDetail(@PathVariable("originId") Long originId,
                              @PathVariable("feedType") FeedType feedType);

    @PostMapping("/clone")
    void cloneSource(@RequestBody SourceCloneRequest sourceCloneRequest);

    @GetMapping("/detail/source/{keyword}")
    List<OriginDto> searchSource(@PathVariable String keyword);

    @GetMapping("/detail/repertory/{keyword}")
    List<OriginDto> searchRepertory(@PathVariable String keyword);

    @GetMapping("/detail/download/{originId}/{memberId}")
    Boolean getIsDownloaded(@PathVariable Long originId, @PathVariable Long memberId);

    // repertory id 보내면 소스 디테일 리스트 날라옴
    @GetMapping("/detail/repertory/source/{originId}")
    List<OriginDto> getSourceList(@PathVariable Long originId);

}
