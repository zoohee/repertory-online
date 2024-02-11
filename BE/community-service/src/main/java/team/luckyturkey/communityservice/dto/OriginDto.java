package team.luckyturkey.communityservice.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OriginDto {
    private Long originId;
    private Long memberId;
    private String feedName;
    private String feedUrl;
    private String feedThumbnailUrl;
    private LocalDateTime feedDate;
}
