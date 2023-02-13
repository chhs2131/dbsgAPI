package com.dbsgapi.dbsgapi.api.auth.service;

import com.dbsgapi.dbsgapi.api.auth.dto.MemberDto;

public interface KakaoOauthService {
    MemberDto login(String kakaoAccessToken);
}
