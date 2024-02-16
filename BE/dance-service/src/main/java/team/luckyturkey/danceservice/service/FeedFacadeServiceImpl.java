package team.luckyturkey.danceservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.luckyturkey.danceservice.controller.responsedto.CommunityFeedResponse;
import team.luckyturkey.danceservice.controller.responsedto.StandardRepertoryResponse;
import team.luckyturkey.danceservice.controller.responsedto.StandardSourceResponse;
import team.luckyturkey.danceservice.domain.FeedType;

@Service
@RequiredArgsConstructor
public class FeedFacadeServiceImpl implements FeedFacadeService{
    private final TagService tagService;
    private final SourceService sourceService;
    private final RepertoryService repertoryService;


    @Override
    public CommunityFeedResponse findFeed(Long originId, FeedType feedType) {
        CommunityFeedResponse response;
        if(feedType == FeedType.SOURCE){
            StandardSourceResponse sourceResponse = sourceService.getSource(originId);
            response = mapCommunityFeedResponse(sourceResponse);
        }
        else if (feedType == FeedType.REPERTORY){
            StandardRepertoryResponse repertoryResponse = repertoryService.getRepertory(originId);
            response = mapCommunityFeedResponse(repertoryResponse);
        }
        else{
            response = new CommunityFeedResponse();
        }
        return response;
    }


    private CommunityFeedResponse mapCommunityFeedResponse(StandardSourceResponse sourceResponse){
        return CommunityFeedResponse.builder()
                .originId(sourceResponse.getSourceId())
                .memberId(sourceResponse.getMemberId())
                .feedName(sourceResponse.getSourceName())
                .feedType(FeedType.SOURCE)
                .feedUrl(sourceResponse.getSourceUrl())
                .build();
    }

    private CommunityFeedResponse mapCommunityFeedResponse(StandardRepertoryResponse repertoryResponse){
        return CommunityFeedResponse.builder()
                .originId(repertoryResponse.getRepertoryId())
                .memberId(repertoryResponse.getMemberId())
                .feedName(repertoryResponse.getRepertoryName())
                .feedType(FeedType.REPERTORY)
                .feedUrl(repertoryResponse.getRepertoryUrl())
                .build();
    }
}
