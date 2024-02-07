package team.luckyturkey.danceservice.controller.requestdto;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class PatchSourceStatusRequest {
    private boolean isAvailable;
}