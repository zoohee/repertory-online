package team.luckyturkey.communityservice.controller.advice;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import team.luckyturkey.communityservice.dto.response.ErrorResponse;
import team.luckyturkey.communityservice.exception.AlreadySubscribedException;
import team.luckyturkey.communityservice.exception.InvalidDataException;
import team.luckyturkey.communityservice.exception.NullException;
import team.luckyturkey.communityservice.util.ErrorCode;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex){
        log.error("handleException",ex);
        ErrorResponse response = new ErrorResponse(ErrorCode.INTER_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AlreadySubscribedException.class)
    public ResponseEntity<ErrorResponse> handleEmailDuplicateException(AlreadySubscribedException ex){
        log.error("alreadySubscribedException", ex);
        ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        response.setMessage("Subscription already exists");
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

    @ExceptionHandler(NullException.class)
    public ResponseEntity<ErrorResponse> handleNullException(NullException ex){
        log.error("nullException", ex);
        ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        response.setMessage("Parameter cannot be null");
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<ErrorResponse> InvalidDataException(InvalidDataException ex){
        log.error("invalidDataException", ex);
        ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        response.setMessage("The data in the database is invalid");
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }
}