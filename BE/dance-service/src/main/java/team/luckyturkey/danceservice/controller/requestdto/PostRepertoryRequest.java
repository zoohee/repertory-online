package team.luckyturkey.danceservice.controller.requestdto;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
public class PostRepertoryRequest {
    private String repertoryName;
    private List<Long> sourceIdList;
    private boolean isRepertoryOpen;

}
