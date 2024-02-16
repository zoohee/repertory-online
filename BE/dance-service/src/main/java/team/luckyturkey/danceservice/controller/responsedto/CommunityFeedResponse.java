package team.luckyturkey.danceservice.controller.responsedto;

import lombok.*;
import team.luckyturkey.danceservice.domain.FeedType;
import java.time.LocalDateTime;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityFeedResponse {
    private Long originId;
    private Long memberId;
    private String feedName;
    private FeedType feedType;
    private String feedUrl;
    private String feedThumbnailUrl;
    private LocalDateTime feedDate;
}
