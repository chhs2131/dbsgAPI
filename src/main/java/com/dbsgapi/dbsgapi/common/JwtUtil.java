package com.dbsgapi.dbsgapi.common;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.CachingUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.Key;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;

public class JwtUtil {
    private final UserDetailsService userDetailsService = new UserDetailsService() {
        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            return null;
        }
    };
    private SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long tokenValidTime = 60 * 30 * 1000L;    // 토큰 유효시간 1달

    public String createJws() throws Exception {
        Date now = new Date();
        Claims claims = Jwts.claims().setSubject("userPk");
        claims.put("user_no", new BigInteger("12455244214224"));
        claims.put("role_name", "user");

        String jws = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // (1)헤더타입 지정
                .setClaims(claims) // setClaims는 기존 PAYLOAD에 덮어쓰기된다. (즉, 뒤쪽에 놓게되면 이전내용이 무시됨)
                .setIssuer("dbsg_api") // (2)토큰발급자 설정
                .setIssuedAt(now) // (3)발급시간 설정(date)
                .setExpiration(new Date(now.getTime() + tokenValidTime)) // (4)만료시간 설정
                //.claim("id", "아이디") // (5)비공개 클레임 설정
                //.claim("email", "ajufresh@gmail.com")
                .signWith(key, SignatureAlgorithm.HS256) // (6)해상 알고리즘 및 시크릿KEY 지정
                .compact(); // 설정값을 바탕으로 JWT를 압축 (JWS형식)

        return jws;
    }

    public String accessJws(String compactJws) throws Exception {
        //test 전용, 이후에는 Filter를 통해 security와 연동하여 검증예정.
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(compactJws);

            //OK, we can trust this JWT
            Date date = claims.getBody().getExpiration();
            String stringDate = date.toString();
            boolean boolExpiration = date.before(new Date());
            String issuer = claims.getBody().getIssuer();
            String jwsHeader = claims.getHeader().toString();
            String userPk = claims.getBody().getSubject();
            return "{'rawToken' :" + compactJws +
                    ", '만료여부' : " + boolExpiration +
                    ", '만료시점' : " + stringDate +
                    ", '발행자' : " + issuer +
                    ", 'JWS헤더' : " + jwsHeader +
                    ", '유저정보' : " + userPk + "}";
        } catch (JwtException e) {
            //don't trust the JWT!
            return "[bad jws] " + compactJws +
                    "\n[Error] " + e;
        }
    }

    //토큰에서 인증정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    //토큰에서 Subject 추출
    public String getUserPk(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    //Request Header에서 Token값 가져오기
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    //토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String validateHeader(String header) {
        validationAuthorizationHeader(header);
        String token = extractToken(header);
        return token;
    }

    //Header에 형식 검사
    private void validationAuthorizationHeader(String header) {
        if (header == null || !header.startsWith("Bearer ")) {
            throw new IllegalArgumentException();
        }
    }

    //Header에 Bearer 문자열 제거 후 token값만
    private String extractToken(String header) {
        return header.substring("Bearer ".length());
    }

    // 필터의 진행
    // HTTP 헤더에서 JWT 토큰값 가져오기
    // 유효한 토큰인지 확인 (baerer)
    // ㄴ 토큰에서 유저정보 불러오기
    // ㄴ SecurityContext 에 Authentication 객체를 저장합니다.

    //https://koogood.tistory.com/25
}
