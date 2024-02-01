package team.luckyturkey.danceservice.controller.requestdto;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class PostCloneRequest {
    private Long originId;
    private Long memberId;
}
