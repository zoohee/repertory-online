package team.luckyturkey.communityservice.dto.response;

import lombok.*;
import team.luckyturkey.communityservice.entity.FeedType;

import java.time.LocalDateTime;
import java.util.Date;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedDetailResponse {
    private Long feedId;
    private FeedType feedType;
    private Long likeCount;
    private Long downloadCount;
    private Boolean feedDisable;

    // originDto
    private Long originId;
    private Long memberId;
    private String feedName;
    private String feedUrl;
    private String feedThumbnailUrl;
    private LocalDateTime feedDate;

    // dancer
    private String memberName;
    private String memberProfile;
}
