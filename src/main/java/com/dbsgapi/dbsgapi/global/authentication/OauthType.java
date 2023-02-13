package com.dbsgapi.dbsgapi.global.authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OauthType {
    KAKAO(1, "kakao", ""),
    GOOGLE(2, "google", ""),
    FACEBOOK(2, "facebook", ""),
    NAVER(2, "naver", ""),
    LINE(2, "line", ""),
    APPLE(2, "apple", ""),
    ;

    private int id;
    private String provider;
    private String desc;
}
