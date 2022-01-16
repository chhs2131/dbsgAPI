package com.dbsgapi.dbsgapi.login.kakao.service;

import com.dbsgapi.dbsgapi.login.dto.MemberDto;
import com.dbsgapi.dbsgapi.login.kakao.dto.KakaoApiUserDto;
import com.dbsgapi.dbsgapi.login.kakao.dto.KakaoMemberDto;
import com.dbsgapi.dbsgapi.login.kakao.dto.KakaoOAuthDto;

public interface KakaoService {
    KakaoOAuthDto getToken(String authCode) throws Exception;
    KakaoApiUserDto getKakaoUid(String accessToken) throws Exception;
    KakaoMemberDto selectKakaoMember(String kakaoUid) throws Exception;
    MemberDto selectMember(long userNo) throws Exception;
    MemberDto insertKakaoMember(KakaoOAuthDto kakaoOAuthDto, KakaoApiUserDto kakaoApiUserDto) throws Exception;
}
