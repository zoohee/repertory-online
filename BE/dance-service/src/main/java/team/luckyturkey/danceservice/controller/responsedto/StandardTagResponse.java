package team.luckyturkey.danceservice.controller.responsedto;

import lombok.*;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StandardTagResponse {
    private Long tagId;
    private String tagName;
}
