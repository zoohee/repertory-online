package team.luckyturkey.projectservice.controller.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import team.luckyturkey.projectservice.controller.responsedto.ErrorResponse;
import team.luckyturkey.projectservice.exception.EmptyResultException;
import team.luckyturkey.projectservice.exception.NullException;
import team.luckyturkey.projectservice.util.ErrorCode;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex){
        log.error("handleException",ex);
        ErrorResponse response = new ErrorResponse(ErrorCode.INTER_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NullException.class)
    public ResponseEntity<ErrorResponse> handleNullException(NullException ex){
        log.error("nullException", ex);
        ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        response.setMessage("Parameter cannot be null");
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

    @ExceptionHandler(EmptyResultException.class)
    public ResponseEntity<ErrorResponse> emptyResultException(EmptyResultException ex){
        log.error("emptyResultException", ex);
        ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        response.setMessage("No database lookup results");
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

}