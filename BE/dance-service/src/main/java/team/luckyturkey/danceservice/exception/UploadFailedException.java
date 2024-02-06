package team.luckyturkey.danceservice.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import team.luckyturkey.danceservice.util.ErrorCode;

@RequiredArgsConstructor
@Getter
public class UploadFailedException extends RuntimeException {
    private final ErrorCode errorCode;
}
