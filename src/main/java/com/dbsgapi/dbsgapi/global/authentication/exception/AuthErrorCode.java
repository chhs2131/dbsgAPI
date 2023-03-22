package com.dbsgapi.dbsgapi.global.authentication.exception;

import com.dbsgapi.dbsgapi.global.response.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum AuthErrorCode implements ErrorCode {
    // jwt
    MALFRMED_JWT_EXCEPTION(UNAUTHORIZED, "J000", "잘못된 JWT 서명입니다."),
    EXPIRED_JWT_EXCEPTION(FORBIDDEN, "J001", "만료된 JWT 토큰입니다."),
    UNSUPPORTED_JWT_EXCEPTION(BAD_REQUEST, "J002", "지원되지 않는 JWT 토큰입니다."),
    ILLEGAL_JWT_EXCEPTION(BAD_REQUEST, "J003", "JWT 토큰이 잘못되었습니다."),
    NULL_POINTER_JWT_EXCEPTION(BAD_REQUEST, "J004", "토큰 값이 비어있습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
