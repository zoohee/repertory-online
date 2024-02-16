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

    public List<FeedDetailResponse> searchFeedByName(String keyword) {
        List<OriginDto> sources = danceServiceClient.searchSource(keyword);
        List<OriginDto> repertories = danceServiceClient.searchRepertory(keyword);
        List<FeedDetailResponse> feeds = new ArrayList<>();

        // source 정보 가져오기
        feeds = getFeedDetailByOriginDto(sources, feeds);

        // repertory 정보 가져오기
        feeds = getFeedDetailByOriginDto(repertories, feeds);

        feeds.sort((o1, o2) -> {
            if (o1.getFeedDate() == null && o2.getFeedDate() == null) {
                return 0;
            } else if (o1.getFeedDate() == null) {
                return 1;
            } else if (o2.getFeedDate() == null) {
                return -1;
            }
            return o2.getFeedDate().compareTo(o1.getFeedDate());
        });

        return feeds;
    }

    public List<FeedDetailResponse> searchFeedByDancerName(String keyword) {
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

            FeedDetailResponse feedDetailResponse = mapFeedDetailResponse(originDto, f, memberDto);
            feedDetailResponseList.add(feedDetailResponse);
        }

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

    private List<FeedDetailResponse> getFeedDetailByOriginDto(List<OriginDto> repertories, List<FeedDetailResponse> feeds) {
        for (OriginDto s : repertories) {
            Feed feed = feedRepository.getFeedByOriginId(s.getOriginId());
            if (feed == null) { continue; }

            // 이미 존재하는 feedId인지 확인
            boolean isExist = feeds.stream().anyMatch(f -> Objects.equals(f.getFeedId(), feed.getId()));
            if (isExist) {
                continue;
            }

            MemberDto memberDto = memberServiceClient.getMemberInfo(feed.getMemberId());
            if (memberDto == null) {
                continue;
            }

            // TODO: dance service query -> only public (disable=true)
            feeds.add(mapFeedDetailResponse(s, feed, memberDto));
        }
        return feeds;
    }

    private FeedDetailResponse mapFeedDetailResponse(OriginDto s, Feed feed, MemberDto memberDto) {

        return FeedDetailResponse.builder()
                .feedId(feed.getId())
                .feedType(FeedType.SOURCE)
                .likeCount(feedLikeCacheRepository.findByFeedId(feed.getId()))
                .downloadCount(feed.getDownloadCount())
                .feedDisable(feed.getFeedDisable())
                .originId(s.getOriginId())
                .memberId(s.getMemberId())
                .feedName(s.getFeedName())
                .feedUrl(s.getFeedUrl())
                .feedThumbnailUrl(s.getFeedThumbnailUrl())
                .feedDate(s.getFeedDate())
                .memberName(memberDto.getMemberName())
                .memberProfile(memberDto.getMemberProfile())
                .build();
    }

}
