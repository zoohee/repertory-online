package team.luckyturkey.communityservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.luckyturkey.communityservice.client.DanceServiceClient;
import team.luckyturkey.communityservice.client.MemberServiceClient;
import team.luckyturkey.communityservice.dto.MemberDto;
import team.luckyturkey.communityservice.dto.OriginDto;
import team.luckyturkey.communityservice.dto.response.FeedDetailResponse;
import team.luckyturkey.communityservice.entity.Feed;
import team.luckyturkey.communityservice.entity.FeedType;
import team.luckyturkey.communityservice.repository.FeedLikeCacheRepository;
import team.luckyturkey.communityservice.repository.FeedRepository;
import team.luckyturkey.communityservice.util.DtoBuilder;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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

    public List<FeedDetailResponse> searchFeedByName(String keyword, Long memberId) {
        List<OriginDto> sources = danceServiceClient.searchSource(keyword);
        List<OriginDto> repertories = danceServiceClient.searchRepertory(keyword);
        List<FeedDetailResponse> feeds = new ArrayList<>();

        // source 정보 가져오기
        feeds = getFeedDetailByOriginDto(sources, feeds, memberId);

        // repertory 정보 가져오기
        feeds = getFeedDetailByOriginDto(repertories, feeds, memberId);

        return sortByFeedDate(feeds);
    }

    public List<FeedDetailResponse> searchFeedByDancerName(String keyword, Long memberId) {
        List<MemberDto> members = memberServiceClient.searchByMemberName(keyword);
        List<Long> ids = members.stream()
                .map(MemberDto::getMemberId)
                .toList();

        List<Feed> feeds = feedRepository.findFeedsByMembers(ids);
        List<FeedDetailResponse> feedDetailResponseList = new ArrayList<>();

        for (Feed f : feeds) {
            OriginDto originDto = danceServiceClient.getOriginDetail(f.getOriginId(), f.getFeedType());
            if (originDto.getOriginId() == null) { continue; }

            // 이미 존재하는 feedId인지 확인
            boolean isExist = feedDetailResponseList
                    .stream()
                    .anyMatch(feed -> Objects.equals(f.getId(), feed.getFeedId()));
            if (isExist) {
                continue;
            }

            MemberDto memberDto = memberServiceClient.getMemberInfo(f.getMemberId());
            if (memberDto == null) {
                continue;
            }

            FeedDetailResponse feedDetailResponse = dtoBuilder.mapFeedDetailResponse(originDto, f, memberDto, memberId);
            feedDetailResponseList.add(feedDetailResponse);
        }

        return sortByFeedDate(feedDetailResponseList);
    }

    private List<FeedDetailResponse> sortByFeedDate(List<FeedDetailResponse> feedDetailResponseList) {
        feedDetailResponseList.sort((o1, o2) -> {
            if (o1.getFeedDate() == null && o2.getFeedDate() == null) {
                return 0;
            } else if (o1.getFeedDate() == null) {
                return 1;
            } else if (o2.getFeedDate() == null) {
                return -1;
            }
            return o2.getFeedDate().compareTo(o1.getFeedDate());
        });

        return feedDetailResponseList;
    }

    private List<FeedDetailResponse> getFeedDetailByOriginDto(List<OriginDto> repertories, List<FeedDetailResponse> feeds, Long memberId) {
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
            feeds.add(dtoBuilder.mapFeedDetailResponse(s, feed, memberDto, memberId));
        }
        return feeds;
    }

}
