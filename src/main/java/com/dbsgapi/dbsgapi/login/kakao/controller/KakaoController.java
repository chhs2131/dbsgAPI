package com.dbsgapi.dbsgapi.login.kakao.controller;

import com.dbsgapi.dbsgapi.login.dto.MemberDto;
import com.dbsgapi.dbsgapi.login.kakao.dto.KakaoApiUserDto;
import com.dbsgapi.dbsgapi.login.kakao.dto.KakaoMemberDto;
import com.dbsgapi.dbsgapi.login.kakao.dto.KakaoOAuthDto;
import com.dbsgapi.dbsgapi.login.kakao.service.KakaoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "KakaoController", description = "카카오 OAuth 로그인을 위한 API")
public class KakaoController {
    @Autowired
    private KakaoService kakaoService;

    @RequestMapping(value="/login/kakaoLoginUrl", method= RequestMethod.GET)
    @ApiOperation(value="백엔드 테스트용", notes="카카오 AuthCode 취득을 위한 URI 반환")
    public String kakaoLoginUrl() throws Exception {
        String kakaoLoginUrl = "https://kauth.kakao.com/oauth/authorize?client_id=e0b6130240281c4b18e88e405545754f&redirect_uri=http://1.243.131.200:8080/login/kakao&response_type=code";
        return kakaoLoginUrl;
    }

    @RequestMapping(value={"/login/kakao"}, method=RequestMethod.GET)
    @ApiOperation(value="카카오 OAuth2.0 (REST:AuthCode)", notes="카카오 AuthCode를 전달하고, 서버의 계정정보값을 취득합니다."
            + "<br/>카카오 로그인시 RedirectURI를 해당으로 설정하여 진행합니다.<br/><br/>단, 카카오 보안규칙상 AuthCode 취득자와 이용자가 동일해야하므로 사용시 주의필요."
            + "<br/>Redirect URI 예시: https://kauth.kakao.com/oauth/authorize?client_id=a130d4bc5b0df2dd600ac87ffdda755a&redirect_uri=http://1.243.131.200:8080/login/kakao&response_type=code")
    @ApiImplicitParam(
            name = "code"
            , value = "카카오에서 부여받은 AuthCode"
            , required = true
            , dataType = "string"
            , paramType = "AuthCode"
            , defaultValue = "")
    public MemberDto kakaoLogin(String code) throws Exception {
        KakaoOAuthDto kakaoOAuthDto = kakaoService.getToken(code);  //Token을 받아옵니다.
        String accessToken = kakaoOAuthDto.getAccessToken();
        return getMemberDto(accessToken, kakaoOAuthDto);
    }

    @RequestMapping(value={"/login/kakaoAccessToken"}, method=RequestMethod.GET)
    @ApiOperation(value="카카오 OAuth2.0 (Android:AccessToken)", notes="카카오 AccessToken을 전달하고, 서버의 계정정보값을 취득합니다.")
    @ApiImplicitParam(
            name = "code"
            , value = "카카오에서 부여받은 accessToken"
            , required = true
            , dataType = "string"
            , paramType = "AuthCode"
            , defaultValue = "")
    public MemberDto kakaoLoginAccessToken(String accessToken) throws Exception {
        KakaoOAuthDto kakaoOAuthDto = new KakaoOAuthDto();
        kakaoOAuthDto.setAccessToken(accessToken);
        return getMemberDto(accessToken, kakaoOAuthDto);
    }

    private MemberDto getMemberDto(String accessToken, KakaoOAuthDto kakaoOAuthDto) throws Exception {
        KakaoApiUserDto kakaoApiUserDto = kakaoService.getKakaoUid(accessToken);  //카카오 계정정보를 받아옵니다.
        KakaoMemberDto kakaoMemberDto = kakaoService.selectKakaoMember(kakaoApiUserDto.getId());  //DB에 기등록된 사용자인지 확인합니다.

        MemberDto memberDto = new MemberDto();
        if(kakaoMemberDto == null) { //DB에 등록되지 않았으면 신규등록합니다.
            System.out.println("신규등록진행");
            memberDto = kakaoService.insertKakaoMember(kakaoOAuthDto, kakaoApiUserDto);
        } else {
            long userNo = kakaoMemberDto.getUserNo();
            memberDto = kakaoService.selectMember(userNo);
        }

        return memberDto;
    }
}
