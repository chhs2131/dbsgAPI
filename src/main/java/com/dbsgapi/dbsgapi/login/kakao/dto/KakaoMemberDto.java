package com.dbsgapi.dbsgapi.login.kakao.dto;

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
