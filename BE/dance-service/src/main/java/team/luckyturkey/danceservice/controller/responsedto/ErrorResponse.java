package team.luckyturkey.danceservice.controller.responsedto;

import lombok.Getter;
import lombok.Setter;
import team.luckyturkey.danceservice.util.ErrorCode;

@Getter
@Setter
public class ErrorResponse {
    private int status;
    private String message;
    private String code;

    public ErrorResponse(ErrorCode errorCode){
        this.status = errorCode.getStatus();
        this.message = errorCode.getMessage();
        this.code = errorCode.getErrorCode();
    }
}