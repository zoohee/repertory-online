package team.luckyturkey.danceservice.service;

import team.luckyturkey.danceservice.controller.responsedto.CommunityFeedResponse;
import team.luckyturkey.danceservice.domain.FeedType;

import java.util.List;

public interface FeedFacadeService {
    CommunityFeedResponse findFeed(Long originId, FeedType feedType);
    List<CommunityFeedResponse> searchSourceWithKeyword(String keyword);
    List<CommunityFeedResponse> searchRepertoryWithKeyword(String keyword);

    List<CommunityFeedResponse> getSourceList(Long originId);
    Boolean getIsDownloaded(Long originId, Long memberId);
}
