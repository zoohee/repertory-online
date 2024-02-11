package team.luckyturkey.communityservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import team.luckyturkey.communityservice.dto.response.FeedDetailResponse;
import team.luckyturkey.communityservice.service.SearchService;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/search")
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/tag/{keyword}")
    public List<FeedDetailResponse> searchFeedByName(@PathVariable String keyword) {
        return searchService.searchSourceByName(keyword);
    }
}
