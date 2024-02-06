package team.luckyturkey.danceservice.controller.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import team.luckyturkey.danceservice.controller.responsedto.ErrorResponse;
import team.luckyturkey.danceservice.exception.UploadFailedException;
import team.luckyturkey.danceservice.util.ErrorCode;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UploadFailedException.class)
    public ResponseEntity<ErrorResponse> handleUploadFailedException(Exception ex){
        log.error("uploadFailedException",ex);
        ErrorResponse response = new ErrorResponse(ErrorCode.INTER_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}