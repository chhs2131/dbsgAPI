package com.dbsgapi.dbsgapi.common;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Key;
import java.time.Duration;
import java.util.Date;

public class JwtUtil {
    Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long tokenValidTime = 60 * 30 * 1000L;    // 토큰 유효시간 1달

    public String createJws() throws Exception {
        Date now = new Date();

        String jws = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // (1)헤더타입 지정
                .setIssuer("dbsg_api") // (2)토큰발급자 설정
                .setIssuedAt(now) // (3)발급시간 설정(date)
                .setExpiration(new Date(now.getTime() + tokenValidTime)) // (4)만료시간 설정
                .claim("id", "아이디") // (5)비공개 클레임 설정
                .claim("email", "ajufresh@gmail.com")
                .signWith(key) // (6)해상 알고리즘 및 시크릿KEY 지정
                .compact(); // 설정값을 바탕으로 JWT를 압축 (JWS형식)

        return jws;
    }

    public String accessJws(String compactJws) throws Exception {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(compactJws);
            //OK, we can trust this JWT
            return "[good jws] " + compactJws;
        } catch (JwtException e) {
            //don't trust the JWT!
            return "[bad jws] " + compactJws + "[Error] " + e;
        }
    }

    // 필터의 진행
    // HTTP 헤더에서 JWT 토큰값 가져오기
    // 유효한 토큰인지 확인 (baerer)
    // ㄴ 토큰에서 유저정보 불러오기
    // ㄴ SecurityContext 에 Authentication 객체를 저장합니다.

    //https://koogood.tistory.com/25
}
