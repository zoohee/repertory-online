package team.luckyturkey.communityservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.luckyturkey.communityservice.client.DanceServiceClient;
import team.luckyturkey.communityservice.client.MemberServiceClient;
import team.luckyturkey.communityservice.dto.MemberDto;
import team.luckyturkey.communityservice.dto.OriginDto;
import team.luckyturkey.communityservice.dto.response.FeedResponse;
import team.luckyturkey.communityservice.entity.Feed;
import team.luckyturkey.communityservice.repository.FeedLikeCacheRepository;
import team.luckyturkey.communityservice.repository.FeedRepository;
import team.luckyturkey.communityservice.util.DtoBuilder;

import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class SearchService {
    private final DanceServiceClient danceServiceClient;
    private final FeedRepository feedRepository;
    private final FeedLikeCacheRepository feedLikeCacheRepository;
    private final MemberServiceClient memberServiceClient;
    private final DtoBuilder dtoBuilder;

    public List<FeedResponse> searchFeedByName(String keyword, Long memberId) {
        List<OriginDto> sources = danceServiceClient.searchSource(keyword);
        List<OriginDto> repertories = danceServiceClient.searchRepertory(keyword);
        List<FeedResponse> feeds = new ArrayList<>();

        // source 정보 가져오기
        feeds = getFeedDetailByOriginDto(sources, feeds, memberId);

        // repertory 정보 가져오기
        feeds = getFeedDetailByOriginDto(repertories, feeds, memberId);

        return sortByFeedDate(feeds);
    }

    public List<FeedResponse> searchFeedByDancerName(String keyword, Long memberId) {
        List<MemberDto> members = memberServiceClient.searchByMemberName(keyword);
        List<Long> ids = members.stream()
                .map(MemberDto::getMemberId)
                .toList();

        List<Feed> feeds = feedRepository.findFeedsByMembers(ids);
        List<FeedResponse> feedResponseList = new ArrayList<>();

        for (Feed f : feeds) {
            OriginDto originDto = danceServiceClient.getOriginDetail(f.getOriginId(), f.getFeedType());
            if (originDto.getOriginId() == null) { continue; }

            // 이미 존재하는 feedId인지 확인
            boolean isExist = feedResponseList
                    .stream()
                    .anyMatch(feed -> Objects.equals(f.getId(), feed.getFeedId()));
            if (isExist) {
                continue;
            }

            MemberDto memberDto = memberServiceClient.getMemberInfo(f.getMemberId());
            if (memberDto == null) {
                continue;
            }

            FeedResponse feedResponse = dtoBuilder.mapFeedResponse(originDto, f, memberDto, memberId);
            feedResponseList.add(feedResponse);
        }

        return sortByFeedDate(feedResponseList);
    }

    private List<FeedResponse> sortByFeedDate(List<FeedResponse> feedResponseList) {
        feedResponseList.sort((o1, o2) -> {
            if (o1.getFeedDate() == null && o2.getFeedDate() == null) {
                return 0;
            } else if (o1.getFeedDate() == null) {
                return 1;
            } else if (o2.getFeedDate() == null) {
                return -1;
            }
            return o2.getFeedDate().compareTo(o1.getFeedDate());
        });

        return feedResponseList;
    }

    private List<FeedResponse> getFeedDetailByOriginDto(List<OriginDto> repertories, List<FeedResponse> feeds, Long memberId) {
        for (OriginDto s : repertories) {
            Feed feed = feedRepository.getFeedByOriginId(s.getOriginId());
            if (feed == null || feed.getFeedDisable()) { continue; }

            // 이미 존재하는 feedId인지 확인
            boolean isExist = feeds.stream().anyMatch(f -> Objects.equals(f.getFeedId(), feed.getId()));
            if (isExist) {
                continue;
            }

            MemberDto memberDto = memberServiceClient.getMemberInfo(feed.getMemberId());
            if (memberDto == null) {
                continue;
            }

            System.out.println(memberDto.toString());
            // TODO: dance service query -> only public (disable=true)
            feeds.add(dtoBuilder.mapFeedResponse(s, feed, memberDto, memberId));
        }
        return feeds;
    }

}
