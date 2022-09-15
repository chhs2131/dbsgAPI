package com.dbsgapi.dbsgapi.api.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.*;

@Controller
public class LoginApi {
    /*
    //공개키 발급
    @ApiOperation(value="공개키 API", notes="로그인이나 회원가입시 쓰이는 공개키 발급")
    @GetMapping("/getPublicKey")
    public PublicKeyResponseDto getPublicKey(HttpServletRequest request) throws NoSuchAlgorithmException, InvalidKeySpecException {
        HttpSession httpSession = request.getSession();
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);

        KeyPair keyPair = generator.genKeyPair();
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        // httpSession(세션) : 서버단에서 관리! -> 개인키가 안전하게 보관됨 -> 이후에 자동적으로 만료되며 소멸되기에 관리에 용이함 // 회원가입에 성공하거나 로그인 했을경우에는 세션에서 개인키를 지워 주면 Best

        httpSession.
    }
     */
}
