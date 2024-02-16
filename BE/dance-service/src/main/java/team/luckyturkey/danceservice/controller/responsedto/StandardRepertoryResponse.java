package team.luckyturkey.danceservice.controller.responsedto;

import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StandardRepertoryResponse {
    private Long repertoryId;
    private Long memberId;
    private String repertoryName;
    private String repertoryUrl;
    private String repertoryThumbnailUrl;
    private LocalDateTime repertoryDate;
}
