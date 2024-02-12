package team.luckyturkey.danceservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.luckyturkey.danceservice.controller.responsedto.CommunityFeedResponse;
import team.luckyturkey.danceservice.controller.responsedto.StandardRepertoryResponse;
import team.luckyturkey.danceservice.controller.responsedto.StandardSourceResponse;
import team.luckyturkey.danceservice.domain.FeedType;
import team.luckyturkey.danceservice.repository.cache.CacheTagRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedFacadeServiceImpl implements FeedFacadeService{

    private final CacheTagRepository cacheTagRepository;
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

    @Override
    public List<CommunityFeedResponse> searchSourceWithKeyword(String keyword) {

        // tag -> source
        List<Long> sourceIdList = cacheTagRepository.findByTag(keyword);

        // source %like%
        List<StandardSourceResponse> sourceResponseList = sourceService.searchSource(keyword);

        sourceResponseList.addAll(sourceService.getSourceList(sourceIdList));
        sourceIdList.addAll(sourceResponseList.stream()
                .map(StandardSourceResponse::getSourceId)
                .toList());

        // source -> repertory
        List<StandardRepertoryResponse> repertoryResponseList = repertoryService.getRepertoriesBySources(sourceIdList);

        // repertory %like
        repertoryResponseList.addAll(repertoryService.searchRepertory(keyword));

        List<CommunityFeedResponse> response = new ArrayList<>();
        for(StandardSourceResponse sr: sourceResponseList){
            response.add(mapCommunityFeedResponse(sr));
        }
        for(StandardRepertoryResponse rr: repertoryResponseList){
            response.add(mapCommunityFeedResponse(rr));
        }
        return response;
    }

    @Override
    public List<CommunityFeedResponse> searchRepertoryWithKeyword(String keyword) {

        List<StandardRepertoryResponse> repertoryResponses = repertoryService.searchRepertory(keyword);
        List<CommunityFeedResponse> response = new ArrayList<>();
        for(StandardRepertoryResponse rr: repertoryResponses){
            response.add(mapCommunityFeedResponse(rr));
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
                .feedThumbnailUrl(sourceResponse.getSourceThumbnailUrl())
                .feedDate(sourceResponse.getSourceDate())
                .build();
    }

    private CommunityFeedResponse mapCommunityFeedResponse(StandardRepertoryResponse repertoryResponse){
        return CommunityFeedResponse.builder()
                .originId(repertoryResponse.getRepertoryId())
                .memberId(repertoryResponse.getMemberId())
                .feedName(repertoryResponse.getRepertoryName())
                .feedType(FeedType.REPERTORY)
                .feedUrl(repertoryResponse.getRepertoryUrl())
                .feedThumbnailUrl(repertoryResponse.getRepertoryThumbnailUrl())
                .feedDate(repertoryResponse.getRepertoryDate())
                .build();
    }
}
