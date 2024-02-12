package team.luckyturkey.communityservice.dto.response;

import lombok.*;
import team.luckyturkey.communityservice.entity.FeedType;
import java.time.LocalDateTime;


@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedDetailResponse implements Comparable<FeedDetailResponse> {
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


    @Override
    public int compareTo(FeedDetailResponse o) {
        // null 값 처리
        if (this.feedDate == null && o.feedDate == null) {
            return 0;
        } else if (this.feedDate == null) {
            return 1;
        } else if (o.feedDate == null) {
            return -1;
        }
        // feedDate를 기준으로 내림차순으로 정렬
        return o.feedDate.compareTo(this.feedDate);
    }
}
