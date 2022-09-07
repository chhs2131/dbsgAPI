package com.dbsgapi.dbsgapi.api.login.service;

import com.dbsgapi.dbsgapi.global.configuration.properties.SocialProperty;
import com.dbsgapi.dbsgapi.global.util.HttpConnection;
import com.dbsgapi.dbsgapi.api.login.dto.MemberDto;
import com.dbsgapi.dbsgapi.api.login.dto.KakaoApiUserDto;
import com.dbsgapi.dbsgapi.api.login.dto.KakaoMemberDto;
import com.dbsgapi.dbsgapi.api.login.dto.KakaoOAuthDto;
import com.dbsgapi.dbsgapi.api.login.mapper.KakaoMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class KakaoServiceImpl implements KakaoService{
    private KakaoMapper loginMapper;
    private final SocialProperty socialProperty;

    HttpConnection httpConnection = new HttpConnection();

    public KakaoOAuthDto getToken(String authCode) throws Exception {
        // 변수 선언
        ObjectMapper objectMapper = new ObjectMapper();
        KakaoOAuthDto kakaoOAuthDto = new KakaoOAuthDto();
        String accessToken = "no_data";
        String redirectUri = "http://server.dbsg.co.kr:8080/login/kakao";

        String apiUrl = socialProperty.getKakao().getLogin().getPath() + "?" +
                "grant_type=authorization_code&" +
                "client_id=" + socialProperty.getKakao().getKey() +
                "&redirect_uri=" + redirectUri +
                "&code=" + authCode;

        // HTTP 통신
        String newResult = httpConnection.get(apiUrl);

        // JSON 해석 및 kakaoOAuthDto 반환
        kakaoOAuthDto = objectMapper.readValue(newResult, KakaoOAuthDto.class);
        return kakaoOAuthDto;
    }

    public KakaoApiUserDto getKakaoUid(String accessToken) throws Exception {
        // 변수 초기화
        accessToken = "Bearer " + accessToken;
        ObjectMapper objectMapper = new ObjectMapper();
        KakaoApiUserDto kakaoApiUserDto = new KakaoApiUserDto();
        String apiUrl = socialProperty.getKakao().getProfile().getPath() + "?property_keys=%5B%22id%22%5D";

        Map<String,String> header = new HashMap<String,String>();
        header.put("Authorization", accessToken);

        // HTTP 통신
        String newResult = httpConnection.get(apiUrl, header);

        // JSON 해석 및 kakaoApiUserDto 반환
        kakaoApiUserDto = objectMapper.readValue(newResult, KakaoApiUserDto.class);
        return kakaoApiUserDto;
    }

    public KakaoMemberDto selectKakaoMember(String kakaoUid) throws Exception {
        return loginMapper.selectKakaoMember(kakaoUid);
    }

    public MemberDto insertKakaoMember(KakaoOAuthDto kakaoOAuthDto, KakaoApiUserDto kakaoApiUserDto) throws Exception {
        //신규 계정 생성
        MemberDto memberDto = new MemberDto();
        loginMapper.insertMember(memberDto);

        //카카오 정보 정리
        KakaoMemberDto kakaoMemberDto = new KakaoMemberDto();
        kakaoMemberDto.setUserNo(memberDto.getUserNo());
        kakaoMemberDto.setKakaoUid(kakaoApiUserDto.getId());

        kakaoMemberDto.setAccessToken(kakaoOAuthDto.getAccessToken());
        //kakaoMemberDto.setRefreshDate(kakaoOAuthDto.get);
        //kakaoMemberDto.setExpiresIn(kakaoOAuthDto.getExpiresIn());
        kakaoMemberDto.setRefreshToken(kakaoOAuthDto.getRefreshToken());
        //kakaoMemberDto.setRefreshTokenExpiresIn(kakaoOAuthDto.getRefreshTokenExpiresIn());

        //카카오 계정정보 등록
        loginMapper.insertKakaoMember(kakaoMemberDto);
        return memberDto;
    }

    public MemberDto selectMember(long userNo) throws Exception {
        return loginMapper.selectMember(userNo);
    }

}
