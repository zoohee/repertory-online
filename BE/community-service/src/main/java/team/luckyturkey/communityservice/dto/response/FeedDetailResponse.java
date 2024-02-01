package team.luckyturkey.communityservice.dto.response;

import lombok.Builder;
import lombok.Data;
import team.luckyturkey.communityservice.entity.FeedType;

import java.util.Date;

@Builder
public class FeedDetailResponse {
    private Long feedId;
    private FeedType feedType;
    private Long likeCount;
    private Long downloadCount;
    private Boolean feedDisable;

    private Long originId;
    private Long memberId;
    private String feedName;
    private String feedUrl;
    private Date feedDate;
}
