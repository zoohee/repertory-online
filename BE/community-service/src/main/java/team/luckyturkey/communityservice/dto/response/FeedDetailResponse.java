package team.luckyturkey.communityservice.dto.response;

import lombok.*;

import java.util.List;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedDetailResponse {
    private FeedResponse feed;
    private ProfileSubscriberResponse profile;
    private List<FeedResponse> sources;
}
