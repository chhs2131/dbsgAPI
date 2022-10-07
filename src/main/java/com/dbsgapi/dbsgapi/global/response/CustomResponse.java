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
        CustomResponse<?> customResponse = CustomResponse.builder()
                .status(errorCode.getStatus().value())
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .body(new ArrayList<>())
                .build();

        // 204(no content)일 경우, 정상반환(200)으로 변경하여 Client에게 공통응답 형식으로 반환하게 만들어준다.
        if (errorCode.getStatus() == HttpStatus.NO_CONTENT)
            return new ResponseEntity<>(customResponse, HttpStatus.OK);
        return new ResponseEntity<>(customResponse, errorCode.getStatus());
    }

    public static <T> CustomResponse of(T data) {
        return CustomResponse.builder()
                .status(HttpStatus.OK.value())
                .code("")
                .message("")
                .body(data)
                .build();
    }
}
