package com.dbsgapi.dbsgapi.api.login.service;

import com.dbsgapi.dbsgapi.api.login.dto.MemberDto;
import com.dbsgapi.dbsgapi.api.login.dto.KakaoApiUserDto;
import com.dbsgapi.dbsgapi.api.login.dto.KakaoMemberDto;
import com.dbsgapi.dbsgapi.api.login.dto.KakaoOAuthDto;

public interface KakaoService {
    KakaoOAuthDto getToken(String authCode) throws Exception;
    KakaoApiUserDto getKakaoUid(String accessToken) throws Exception;
    KakaoMemberDto selectKakaoMember(String kakaoUid) throws Exception;
    MemberDto selectMember(long userNo) throws Exception;
    MemberDto insertKakaoMember(KakaoOAuthDto kakaoOAuthDto, KakaoApiUserDto kakaoApiUserDto) throws Exception;
}
