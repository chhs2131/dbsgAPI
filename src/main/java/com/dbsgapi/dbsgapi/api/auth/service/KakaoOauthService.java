package com.dbsgapi.dbsgapi.api.auth.service;

import com.dbsgapi.dbsgapi.api.auth.dto.KakaoProfileDto;
import com.dbsgapi.dbsgapi.api.auth.dto.KakaoTokenInfoDto;

public interface KakaoOauthService {
    KakaoTokenInfoDto getTokenInformation(String kakaoAccessToken);
    KakaoProfileDto getProfile(String kakaoAccessToken);
}