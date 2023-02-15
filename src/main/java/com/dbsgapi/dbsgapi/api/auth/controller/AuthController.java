package com.dbsgapi.dbsgapi.api.auth.controller;

import com.dbsgapi.dbsgapi.api.auth.dto.KakaoProfileDto;
import com.dbsgapi.dbsgapi.api.auth.dto.KakaoTokenInfoDto;
import com.dbsgapi.dbsgapi.api.auth.dto.MemberDto;
import com.dbsgapi.dbsgapi.api.auth.service.KakaoOauthService;
import com.dbsgapi.dbsgapi.global.authentication.AuthResponse;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Auth", description = "로그인 및 계정 관리를 위한 API")
public class AuthController {
    private final KakaoOauthService kakaoOauthService;

    @PostMapping("/oauth/kakao")
    public ResponseEntity<AuthResponse> oauthLoginKakao(@RequestParam String kakaoAccessToken) {
        AuthResponse result = kakaoOauthService.login(kakaoAccessToken);
        System.out.println(result.toString());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Hidden
    @PostMapping("/oauth/google")
    public String oauthLoginGoogle() {
        return null;
    }

    @Hidden
    @PostMapping("/login")
    public String loginDbsg() {
        return null;
    }
}
