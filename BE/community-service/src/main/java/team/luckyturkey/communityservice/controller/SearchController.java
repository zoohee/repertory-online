package team.luckyturkey.communityservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import team.luckyturkey.communityservice.dto.response.FeedDetailResponse;
import team.luckyturkey.communityservice.service.JwtUtil;
import team.luckyturkey.communityservice.service.SearchService;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/search")
public class SearchController {

    private final SearchService searchService;
    private final JwtUtil jwtUtil;

    @GetMapping("/tag/{keyword}")
    public List<FeedDetailResponse> searchFeedByName(@PathVariable String keyword, @RequestHeader("Authorization") final String accessToken) {
        // TODO: Request Header jwt에서 memberId 받아 오기 :done
        String atc = accessToken.split(" ")[1];

        Long memberId = jwtUtil.getMemberId(atc);

        return searchService.searchFeedByName(keyword, memberId);
    }

    @GetMapping("/dancer/{keyword}")
    public List<FeedDetailResponse> searchFeedByDancerName(@PathVariable String keyword, @RequestHeader("Authorization") final String accessToken) {
        // TODO: Request Header jwt에서 memberId 받아 오기 :done
        String atc = accessToken.split(" ")[1];

        Long memberId = jwtUtil.getMemberId(atc);

        return searchService.searchFeedByDancerName(keyword, memberId);
    }

}
