package com.dbsgapi.dbsgapi.global.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Getter
@Builder
public final class CustomResponse<T> {
    private int status;
    private String code;
    private String message;
    private T body;
    private final LocalDateTime timestamp = LocalDateTime.now();

    public static ResponseEntity<CustomResponse> fromErrorCode(ErrorCode errorCode) {
        return new ResponseEntity<>(CustomResponse.builder()
                .status(errorCode.getStatus().value())
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .body(new ArrayList<>())
                .build(),
                errorCode.getStatus());
    }

    public static <T> ResponseEntity<CustomResponse> of(T data) {
        return new ResponseEntity<>(CustomResponse.builder()
                .status(HttpStatus.OK.value())
                .code("")
                .message("")
                .body(data)
                .build(),
                HttpStatus.OK);
    }

    // return new ResponseEntity<>(DbsgApiResponse.fromErrorCode(e.getErrorCode()), e.getErrorCode().getStatus());
    //
}
