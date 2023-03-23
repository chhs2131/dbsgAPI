package com.dbsgapi.dbsgapi.api.auth.exception;

import com.dbsgapi.dbsgapi.api.auth.dto.KakaoErrorDto;
import com.dbsgapi.dbsgapi.global.response.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

import static org.springframework.http.HttpStatus.*;

@Getter
@ToString
@AllArgsConstructor
public enum KakaoApiErrorCode implements ErrorCode {
    // General
    INTERNAL_ERROR(BAD_REQUEST, "-1","재시도하세요."),
    PARAMETER_ERROR(BAD_REQUEST, "-2", "요청 파라미터를 확인하세요."),
    OPTION_UNABLE_ERROR(FORBIDDEN, "-3", "카카오 설정 - 내 애플리케이션 - 필요한기능 활성화가 필요합니다."),
    BLOCK_USER_ERROR(FORBIDDEN, "-4", "제재된 계정일 수 있습니다."),
    HAVE_NOT_PERMISSION_ERROR(FORBIDDEN, "-5", "해당 API의 이해하기 문서를 참고하여 검수 진행, 권한 획득 후 재호출하세요."),
    SERVICE_CHECK_ERROR(BAD_REQUEST, "-7", "서비스 점검 또는 내부 문제가 있으니 공지사항을 확인하세요."),
    ILLEGAL_HEADER_ERROR(BAD_REQUEST, "-8", "올바르지 않은 헤더입니다."),
    SERVICE_NOT_FOUND_ERROR(BAD_REQUEST, "-9", "서비스 종료된 API 입니다."),
    API_CALL_LIMIT_ERROR(BAD_REQUEST, "-10", "API 요청 가능 횟수를 초과하였습니다."),
    SERVICE_KEY_ERROR(UNAUTHORIZED, "-401", "앱키 확인 또는 토큰 갱신, 개발자 사이트에 등록된 앱 정보를 확인해주세요."),
    KAKAO_USER_ERROR(BAD_REQUEST, "-501", "카카오톡 미가입 또는 유예 사용자입니다."),
    IMAGE_SIZE_ERROR(BAD_REQUEST, "-602", "이미지 파일 크기가 업로드 가능한 최대 용량을 초과하였습니다."),
    TIMEOUT_ERROR(BAD_REQUEST, "-603", "요청 처리 중 타임아웃이 발생하였스빈다."),
    TOO_MANY_IMAGE_ERROR(BAD_REQUEST, "-606", "업로드 할 수 있는 최대 이미지 개수를 초과하였습니다."),
    UNREGISTERED_KEY_ERROR(BAD_REQUEST, "-903", "등록되지않은 앱키 또는 엑세스 토큰입니다."),
    IMAGE_FORMAT_ERROR(BAD_REQUEST, "-911", "지원하지 않는 이미지 파일 형식입니다."),
    SYSTEM_CHECK_ERROR(SERVICE_UNAVAILABLE, "-9798", "서비스 점검중"),

    // Login
    LOGIN_UNREGISTERED_ERROR(BAD_REQUEST, "-101", "카카오계쩡 연결 후 재시도하세요."),
    LOGIN_ALREADY_LINKED_ERROR(BAD_REQUEST, "-102", "이미 앱과 연결되어있는 사용자의 토큰입니다."),
    LOGIN_DORMANT_USER_ERROR(BAD_REQUEST, "-103", "휴면 상태 또는 존재하지 않는 카카오 계정입니다."),
    LOGIN_WRONG_PROPERTY_ERROR(BAD_REQUEST, "-201", "요청할 수 없는 값입니다. (내 애플리케이션 - 카카오 로그인 - 사용자 프로퍼티 확인)"),
    LOGIN_NOT_AGREE_ERROR(FORBIDDEN, "-402", "사용자의 동의가 필요한 리소스입니다. (required_scopes 확인)"),
    LOGIN_NO_CHILD_ERROR(UNAUTHORIZED, "-401", "14세 미만 사용자는 호츨 할 수 없게 설정되어있습니다.")
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;

    public static KakaoApiErrorCode from(KakaoErrorDto kakaoErrorDto) {
        String code = String.valueOf(kakaoErrorDto.getCode());
        return from(code);
    }

    public static KakaoApiErrorCode from(String code) {
        return Arrays.stream(KakaoApiErrorCode.values())
                .filter(kakaoApiErrorCode -> kakaoApiErrorCode.getCode().equals(code))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("지원x"));
    }
}
