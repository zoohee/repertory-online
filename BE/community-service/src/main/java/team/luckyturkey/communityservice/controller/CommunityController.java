package team.luckyturkey.communityservice.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import team.luckyturkey.communityservice.dto.OriginDto;
import team.luckyturkey.communityservice.dto.response.FeedDetailResponse;
import team.luckyturkey.communityservice.entity.Feed;
import team.luckyturkey.communityservice.entity.LikeLog;
import team.luckyturkey.communityservice.service.FeedService;
import team.luckyturkey.communityservice.service.LikeService;
import team.luckyturkey.communityservice.service.SubscribeService;

@RestController
@RequiredArgsConstructor
public class CommunityController {

    private final SubscribeService subscribeService;
    private final LikeService likeService;
    private final FeedService feedService;

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

    @DeleteMapping("/subscribe")
    public void unsubscribe(@RequestBody Map<String, Long> data) {
        // TODO: Request Header jwt에서 memberId 받아 오기
        Long memberId = 5678L;
        Long selectedMemberId = data.get("selectedMemberId");

        subscribeService.unsubscribe(memberId, selectedMemberId);
    }

    @PatchMapping("/source/{feedId}/like")
    public Long likeSource(@PathVariable("feedId") Long feedId) {
        // TODO: Request Header jwt에서 memberId 받아 오기
        Long memberId = 5678L;

        LikeLog likeLog = LikeLog.builder()
                .memberId(memberId)
                .feedId(feedId)
                .likeActive(1)
                .timestamp(new Date())
                .build();

        // TODO: DB 저장은 비동기 처리
        likeService.insertLikeLog(likeLog);
        return likeService.insertLikeCache(feedId);
    }

    @DeleteMapping("/source/{feedId}/like")
    public Long cancelLikeSource(@PathVariable("feedId") Long feedId) {
        // TODO: Request Header jwt에서 memberId 받아 오기
        Long memberId = 5678L;

        LikeLog likeLog = LikeLog.builder()
                .memberId(memberId)
                .feedId(feedId)
                .likeActive(0)
                .timestamp(new Date())
                .build();

        // TODO: DB 저장은 비동기 처리
        likeService.insertLikeLog(likeLog);
        return likeService.cancelLikeCache(feedId);
    }


    @GetMapping("/feed/{page}/{pageSize}")
    public List<Feed> getUserFeedList(@PathVariable("page") int page,
                                      @PathVariable("pageSize") int pageSize) {
        return new ArrayList<>();
    }

    @PostMapping("/feed")
    public void insertFeed(@RequestBody Feed feed) {
        feed.setLikeCount(0L);
        feed.setDownloadCount(0L);
        feedService.insertFeed(feed);
    }

    @GetMapping("/feed/{feedId}/detail")
    public FeedDetailResponse getFeedDetail(@PathVariable("feedId") Long feedId) {

        System.out.println(feedId);
        Feed feed = feedService.getFeedDetail(feedId);
        Long originId = feed.getOriginId();
        OriginDto originDto = feedService.getOriginDto(originId, feed.getFeedType());

        return FeedDetailResponse.builder()
                .feedId(feedId)
                .feedType(feed.getFeedType())
                .likeCount(feed.getLikeCount())
                .downloadCount(feed.getDownloadCount())
                .feedDisable(feed.getFeedDisable())
                .originId(originId)
                .memberId(originDto.getMemberId())
                .feedName(originDto.getFeedName())
                .feedUrl(originDto.getFeedUrl())
                .feedDate(originDto.getFeedDate())
                .build();
    }


}
