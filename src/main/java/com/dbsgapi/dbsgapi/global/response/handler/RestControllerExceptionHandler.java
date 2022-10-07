package com.dbsgapi.dbsgapi.global.response.handler;

import com.dbsgapi.dbsgapi.global.response.CustomResponse;
import com.dbsgapi.dbsgapi.global.response.CustomException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@Getter
@RestControllerAdvice
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {CustomException.class})
    protected ResponseEntity<CustomResponse> handleCustomException(CustomException e) {
        log.error("handleCustomException throw CustomException : {}", e.getErrorCode());
        return CustomResponse.fromErrorCode(e.getErrorCode());
    }
}
