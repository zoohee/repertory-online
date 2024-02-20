package team.luckyturkey.communityservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import team.luckyturkey.communityservice.dto.response.FeedResponse;
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
    public List<FeedResponse> searchFeedByName(@PathVariable String keyword,
                                               @RequestHeader("Authorization") final String accessToken) {
        Long memberId = jwtUtil.extractMemberIdFromToken(accessToken);

        return searchService.searchFeedByName(keyword, memberId);
    }

    @GetMapping("/dancer/{keyword}")
    public List<FeedResponse> searchFeedByDancerName(@PathVariable String keyword,
                                                     @RequestHeader("Authorization") final String accessToken) {
        Long memberId = jwtUtil.extractMemberIdFromToken(accessToken);

        return searchService.searchFeedByDancerName(keyword, memberId);
    }

}
