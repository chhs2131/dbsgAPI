package com.dbsgapi.dbsgapi.global.authentication;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@ToString
public class AuthResponse {
    private final String accessToken;
    private final LocalDateTime expireTime;
    private final LocalDateTime issuedTime;

    public AuthResponse(String accessToken, LocalDateTime expireTime) {
        this.accessToken = accessToken;
        this.expireTime = expireTime;
        this.issuedTime = LocalDateTime.now();
    }
}
