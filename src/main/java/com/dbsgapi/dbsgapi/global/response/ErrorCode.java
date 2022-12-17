package com.dbsgapi.dbsgapi.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // test
    TEST_EXCEPTION(NOT_FOUND, "T000", "yeah"),

    // ipo
    IPO_LIST_NOT_FOUND_EXCEPTION(NO_CONTENT, "II000", "조건에 맞는 조회 결과가 없습니다."),
    IPO_LIST_NOT_SUPPORTED_STATE(NOT_IMPLEMENTED, "II002", "지원되지 않는 state parameter 입니다."),
    IPO_DETAIL_NOT_FOUND_EXCEPTION(NOT_FOUND, "II001", "해당하는 결과가 없습니다."),
    IPO_SCHEDULE_NOT_FOUND_EXCEPTION(NO_CONTENT, "IS000", "조건에 맞는 조회 결과가 없습니다."),
    IPO_COMMENT_LIST_NOT_FOUND_EXCEPTION(NO_CONTENT, "IC000", "조건에 맞는 조회 결과가 없습니다."),
    IPO_COMMENT_NOT_FOUND_EXCEPTION(NOT_FOUND, "IC001", "해당하는 결과가 없습니다."),
    IPO_COMMENT_WRONG_PARAMETER_EXCEPTION(BAD_REQUEST, "IC100", "조회 대상이 올바르지 않습니다. (ipo index 오류)"),
    IPO_UNDERWRITER_NOT_FOUND_EXCEPTION(NOT_FOUND, "IU000", "해당하는 결과가 없습니다."),

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
