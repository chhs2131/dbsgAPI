package com.dbsgapi.dbsgapi.api.login.dto;

import lombok.Data;

@Data
public class KakaoMemberDto {
    private String kakaoUid;
    private long userNo;
    private String accessToken;
    private String refreshToken;
    private String registDate;
    private String refreshDate;
    private String expiresIn;
    private String refreshTokenExpiresIn;
}
