package com.dbsgapi.dbsgapi.api.auth.controller;

import com.dbsgapi.dbsgapi.api.auth.dto.KakaoProfileDto;
import com.dbsgapi.dbsgapi.api.auth.dto.KakaoTokenInfoDto;
import com.dbsgapi.dbsgapi.api.auth.service.KakaoOauthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final KakaoOauthService kakaoOauthService;

    @PostMapping("/oauth/kakaoTokenInfo")
    public ResponseEntity<KakaoTokenInfoDto> oauthTokenTestKakao(@RequestParam String kakaoAccessToken) {
        // 현재 토큰 정보 조회로 통신 테스트만 진행
        KakaoTokenInfoDto result = kakaoOauthService.getTokenInformation(kakaoAccessToken);
        System.out.println(result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/oauth/kakaoProfile")
    public ResponseEntity<KakaoProfileDto> oauthProfileTestKakao(@RequestParam String kakaoAccessToken) {
        KakaoProfileDto result = kakaoOauthService.getProfile(kakaoAccessToken);
        System.out.println(result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/oauth/kakao")
    public ResponseEntity<KakaoProfileDto> oauthLoginKakao(@RequestParam String kakaoAccessToken) {
        KakaoProfileDto result = kakaoOauthService.getProfile(kakaoAccessToken);
        System.out.println(result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/oauth/google")
    public String oauthLoginGoogle() {
        return null;
    }

    @PostMapping("/login")
    public String loginDbsg() {
        return null;
    }
}
