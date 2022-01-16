package com.dbsgapi.dbsgapi.login.kakao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class KakaoOAuthDto {
    @JsonProperty("token_type")
    private String tokenType;
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("expires_in")
    private int expiresIn;
    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty("refresh_token_expires_in")
    private int refreshTokenExpiresIn;
    private String scope;
}
