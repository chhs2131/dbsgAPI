package com.dbsgapi.dbsgapi.api.auth.service;

import com.dbsgapi.dbsgapi.api.auth.dto.MemberDto;
import com.dbsgapi.dbsgapi.global.authentication.AuthResponse;

public interface KakaoOauthService {
    AuthResponse login(String kakaoAccessToken);
}
