package team.luckyturkey.danceservice.controller.responsedto;

import lombok.*;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StandardRepertoryResponse {
    private Long repertoryId;
    private String repertoryName;
    private String repertoryUrl;
    private String repertoryThumbnailUrl;
}
