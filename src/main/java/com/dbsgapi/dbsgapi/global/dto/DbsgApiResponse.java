package com.dbsgapi.dbsgapi.global.dto;

import com.dbsgapi.dbsgapi.global.error.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Getter
@Builder
public final class DbsgApiResponse<T> {
    private int status;
    private String code;
    private String message;
    private T body;
    private final LocalDateTime timestamp = LocalDateTime.now();

    public static DbsgApiResponse fromErrorCode(ErrorCode errorCode) {
        return DbsgApiResponse.builder()
                .status(errorCode.getStatus().value())
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .body(new ArrayList<>())
                .build();
    }

    public static <T> DbsgApiResponse of(T data) {
        return DbsgApiResponse.builder()
                .status(HttpStatus.OK.value())
                .code("")
                .message("")
                .body(data)
                .build();
    }
}
