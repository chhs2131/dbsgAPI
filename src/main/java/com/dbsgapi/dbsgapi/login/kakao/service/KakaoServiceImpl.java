package com.dbsgapi.dbsgapi.login.kakao.service;

import com.dbsgapi.dbsgapi.common.HttpConnection;
import com.dbsgapi.dbsgapi.login.dto.MemberDto;
import com.dbsgapi.dbsgapi.login.kakao.dto.KakaoApiUserDto;
import com.dbsgapi.dbsgapi.login.kakao.dto.KakaoMemberDto;
import com.dbsgapi.dbsgapi.login.kakao.dto.KakaoOAuthDto;
import com.dbsgapi.dbsgapi.login.kakao.mapper.KakaoMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class KakaoServiceImpl implements KakaoService{

    @Autowired
    private KakaoMapper loginMapper;

    String kakaoRestKey = "e0b6130240281c4b18e88e405545754f";
    HttpConnection httpConnection = new HttpConnection();

    public KakaoOAuthDto getToken(String authCode) throws Exception {
        // 변수 선언
        ObjectMapper objectMapper = new ObjectMapper();
        KakaoOAuthDto kakaoOAuthDto = new KakaoOAuthDto();
        String accessToken = "no_data";
        String redirectUri = "http://test.dbsg.co.kr:8080/login/kakao";

        String apiUrl = "https://kauth.kakao.com/oauth/token?" +
                "grant_type=authorization_code&" +
                "client_id=" + kakaoRestKey +
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
        String apiUrl = "https://kapi.kakao.com/v2/user/me?property_keys=%5B%22id%22%5D";

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
