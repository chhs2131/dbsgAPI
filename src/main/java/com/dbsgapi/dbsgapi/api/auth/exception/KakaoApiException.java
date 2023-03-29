package com.dbsgapi.dbsgapi.api.auth.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KakaoApiException extends RuntimeException {
    private final KakaoApiErrorCode kakaoApiErrorCode;
}
