package team.luckyturkey.communityservice.dto.response;

import lombok.*;

import java.util.List;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedRepertoryDetailResponse {
    private FeedDetailResponse feed;
    private ProfileSubscriberResponse profile;
    private List<FeedDetailResponse> sources;
}
