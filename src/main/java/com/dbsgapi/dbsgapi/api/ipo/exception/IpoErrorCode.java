package com.dbsgapi.dbsgapi.api.ipo.exception;

import com.dbsgapi.dbsgapi.global.response.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum IpoErrorCode implements ErrorCode {
    // ipo
    IPO_ILLEGAL_ARGUMENT_EXCEPTION(BAD_REQUEST, "IG000", "입력된 조건에 문제가 있습니다."),
    IPO_LIST_NOT_FOUND_EXCEPTION(NO_CONTENT, "II000", "조건에 맞는 조회 결과가 없습니다."),
    IPO_LIST_NOT_SUPPORTED_STATE(NOT_IMPLEMENTED, "II002", "지원되지 않는 state parameter 입니다."),
    IPO_DETAIL_NOT_FOUND_EXCEPTION(NOT_FOUND, "II001", "해당하는 결과가 없습니다."),
    IPO_SCHEDULE_NOT_FOUND_EXCEPTION(NO_CONTENT, "IS000", "조건에 맞는 조회 결과가 없습니다."),
    IPO_COMMENT_LIST_NOT_FOUND_EXCEPTION(NO_CONTENT, "IC000", "조건에 맞는 조회 결과가 없습니다."),
    IPO_COMMENT_NOT_FOUND_EXCEPTION(NOT_FOUND, "IC001", "해당하는 결과가 없습니다."),
    IPO_COMMENT_WRONG_PARAMETER_EXCEPTION(BAD_REQUEST, "IC100", "조회 대상이 올바르지 않습니다. (ipo index 오류)"),
    IPO_UNDERWRITER_NOT_FOUND_EXCEPTION(NOT_FOUND, "IU000", "해당하는 결과가 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
