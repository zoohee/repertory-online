package team.luckyturkey.communityservice.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import team.luckyturkey.communityservice.util.ErrorCode;

@RequiredArgsConstructor
@Getter
public class NotExistsException extends RuntimeException {
    private final ErrorCode errorCode;
}
