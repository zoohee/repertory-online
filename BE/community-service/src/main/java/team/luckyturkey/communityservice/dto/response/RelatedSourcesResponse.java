package team.luckyturkey.communityservice.dto.response;

import lombok.*;
import team.luckyturkey.communityservice.entity.Feed;

import java.util.List;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RelatedSourcesResponse {
    private FeedDetailResponse feed;
    private boolean isFollowed;
    private List<FeedDetailResponse> sources;
}
