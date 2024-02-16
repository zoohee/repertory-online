package team.luckyturkey.danceservice.service;

import team.luckyturkey.danceservice.controller.responsedto.CommunityFeedResponse;
import team.luckyturkey.danceservice.domain.FeedType;

public interface FeedFacadeService {
    CommunityFeedResponse findFeed(Long originId, FeedType feedType);
}
