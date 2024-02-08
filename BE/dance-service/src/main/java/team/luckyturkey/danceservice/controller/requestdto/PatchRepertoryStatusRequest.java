package team.luckyturkey.danceservice.controller.requestdto;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class PatchRepertoryStatusRequest {
    private Boolean isAvailable;
}
