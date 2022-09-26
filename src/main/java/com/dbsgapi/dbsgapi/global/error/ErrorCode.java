package com.dbsgapi.dbsgapi.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // test
    TEST_EXCEPTION(NOT_FOUND, "T000", "yeah"),

    // ipo
    IPO_LIST_NOT_FOUND_EXCEPTION(NOT_FOUND, "II000", "조건에 맞는 조회 결과가 없습니다."),
    IPO_DETAIL_NOT_FOUND_EXCEPTION(NOT_FOUND, "ID000", "조회 결과가 없습니다."),
    IPO_SCHEDULE_NOT_FOUND_EXCEPTION(NOT_FOUND, "IS000", "조건에 맞는 조회 결과가 없습니다."),
    IPO_COMMENT_NOT_FOUND_EXCEPTION(NOT_FOUND, "IC000", "조건에 맞는 조회 결과가 없습니다."),
    IPO_UNDERWRITER_NOT_FOUND_EXCEPTION(NOT_FOUND, "IU000", "조회 결과가 없습니다."),

    // jwt
    MALFRMED_JWT_EXCEPTION(NOT_FOUND, "J000", "잘못된 JWT 서명입니다."),
    EXPIRED_JWT_EXCEPTION(NOT_FOUND, "J001", "만료된 JWT 토큰입니다."),
    UNSUPPORTED_JWT_EXCEPTION(NOT_FOUND, "J002", "지원되지 않는 JWT 토큰입니다."),
    ILLEGAL_JWT_EXCEPTION(NOT_FOUND, "J003", "JWT 토큰이 잘못되었습니다."),
    NULL_POINTER_JWT_EXCEPTION(NOT_FOUND, "J004", "토큰 값이 비어있습니다.")
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
