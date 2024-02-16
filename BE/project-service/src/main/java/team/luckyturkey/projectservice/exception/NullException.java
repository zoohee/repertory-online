package team.luckyturkey.projectservice.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import team.luckyturkey.projectservice.util.ErrorCode;

@RequiredArgsConstructor
@Getter
public class NullException extends RuntimeException {
    private final ErrorCode errorCode;
}
