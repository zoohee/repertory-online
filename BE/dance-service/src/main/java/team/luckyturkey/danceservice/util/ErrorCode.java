package team.luckyturkey.danceservice.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    NOT_FOUND(404,"COMMON-ERR-404","PAGE NOT FOUND"),
    INTER_SERVER_ERROR(500,"COMMON-ERR-500","INTER SERVER ERROR"),
    DUPLICATE_DATA(409, "DB-ERR-409", "DUPLICATE DATA"),
    ;

    private final int status;
    private final String errorCode;
    private final String message;
}