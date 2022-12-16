package com.dbsgapi.dbsgapi.api.login.controller;

import com.dbsgapi.dbsgapi.api.login.dto.KakaoApiUserDto;
import com.dbsgapi.dbsgapi.api.login.dto.KakaoMemberDto;
import com.dbsgapi.dbsgapi.api.login.dto.KakaoOAuthDto;
import com.dbsgapi.dbsgapi.api.login.dto.MemberDto;
import com.dbsgapi.dbsgapi.api.login.service.KakaoService;
import com.dbsgapi.dbsgapi.global.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "카카오 계정", description = "카카오 OAuth 로그인을 위한 API")
public class KakaoController {
    private final JwtUtil jwtUtil;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final KakaoService kakaoService;

    //TODO login 관련도 /api/v1/login 쪽으로 분리 필요함
    @GetMapping(value = "/login/kakaoLoginUrl")
    @Operation(summary = "백엔드 테스트용", description = "카카오 AuthCode 취득을 위한 URI 반환")
    public String kakaoLoginUrl() {
        String kakaoLoginUrl = "https://kauth.kakao.com/oauth/authorize?client_id=e0b6130240281c4b18e88e405545754f&redirect_uri=http://server.dbsg.co.kr:8080/login/kakao&response_type=code";
        return kakaoLoginUrl;
    }

    @GetMapping(value = {"/login/kakao"})
    @Operation(summary = "카카오 OAuth2.0 (REST:AuthCode)", description = "카카오 AuthCode를 전달하고, 서버의 계정정보값을 취득합니다."
            + "<br/>카카오 로그인시 RedirectURI를 해당으로 설정하여 진행합니다.<br/><br/>단, 카카오 보안규칙상 AuthCode는 일회용이므로 사용시 주의필요."
            + "<br/>Redirect URI 예시: https://kauth.kakao.com/oauth/authorize?client_id=a130d4bc5b0df2dd600ac87ffdda755a&redirect_uri=http://1.243.131.200:8080/login/kakao&response_type=code")
    public MemberDto kakaoLogin(@Parameter(description = "카카오에서 발급받은 인증코드(AuthCode)") String code) throws Exception {
        KakaoOAuthDto kakaoOAuthDto = kakaoService.getToken(code);  //kakao로부터 OAuth 정보를 받아옵니다.
        return getMemberDto(kakaoOAuthDto);
    }

    @GetMapping(value = {"/login/kakaoAccessToken"})
    @Operation(summary = "카카오 OAuth2.0 (Android:AccessToken)", description = "카카오 AccessToken을 전달하고, 서버의 계정정보값을 취득합니다. (Target Client: Android)")
    public MemberDto kakaoLoginAccessToken(@Parameter(description = "카카오에서 발급받은 엑세스토큰") String accessToken) throws Exception {
        KakaoOAuthDto kakaoOAuthDto = new KakaoOAuthDto();
        kakaoOAuthDto.setAccessToken(accessToken);
        return getMemberDto(kakaoOAuthDto);
    }

    //TODO 관련 로직 서비스 쪽으로 분리 필요함
    private MemberDto getMemberDto(KakaoOAuthDto kakaoOAuthDto) throws Exception {
        //accessToken을 통해 각각의 서버(카카오, dbsg)에서 정보를 가져옵니다. (신규계정일 경우 등록)
        String accessToken = kakaoOAuthDto.getAccessToken();
        KakaoApiUserDto kakaoApiUserDto = kakaoService.getKakaoUid(accessToken);  //카카오 계정정보를 받아옵니다.
        KakaoMemberDto kakaoMemberDto = kakaoService.selectKakaoMember(kakaoApiUserDto.getId());  //DB에 기등록된 사용자인지 확인합니다.

        MemberDto memberDto = new MemberDto();
        if (kakaoMemberDto == null) { //DB에 등록되지 않았으면 신규등록합니다.
            memberDto = kakaoService.insertKakaoMember(kakaoOAuthDto, kakaoApiUserDto);
        } else { //기존계정이 있는경우 정보를 불러옵니다.
            long userNo = kakaoMemberDto.getUserNo();
            memberDto = kakaoService.selectMember(userNo);
        }

        //보안객체 등록
        //Authentication authentication = this.getAuthentication(memberDto);
        //SecurityContextHolder.getContext().setAuthentication(authentication);

        //JWT 토큰을 추가하고 Return 한다.
        //String token = jwtUtil.createJws(authentication);
        String token = jwtUtil.createJws(Long.toString(memberDto.getUserNo()));
        memberDto.setJwt(token);
        return memberDto;
    }

    private Authentication getAuthentication(MemberDto memberDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(memberDto.getUserNo(), "");

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        return authentication;
    }
}
