package com.dbsgapi.dbsgapi.global.error.handler;

import com.dbsgapi.dbsgapi.global.dto.DbsgApiResponse;
import com.dbsgapi.dbsgapi.global.error.CustomException;
import com.dbsgapi.dbsgapi.global.error.ErrorResponse;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@Getter
@RestControllerAdvice
public class RestControllerExceptionHandler  extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {CustomException.class})
    protected ResponseEntity<DbsgApiResponse> handleCustomException(CustomException e) {
        log.error("handleCustomException throw CustomException : {}", e.getErrorCode());
        return DbsgApiResponse.fromErrorCode(e.getErrorCode());
    }
}
