package com.dbsgapi.dbsgapi.global.util;

import com.dbsgapi.dbsgapi.global.authentication.AuthResponse;
import com.dbsgapi.dbsgapi.global.authentication.MemberPermission;
import com.dbsgapi.dbsgapi.global.configuration.properties.JwtProperty;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public final class JwtUtil {
    final JwtProperty jwtProperty;

    private final String AUTHORITIES_KEY;
    private final String secret;
    private final long tokenValidTime;
    private final String header;

    byte[] keyBytes;
    private SecretKey key;

    @Autowired
    public JwtUtil(JwtProperty jwtProperty) {
        this.jwtProperty = jwtProperty;

        this.AUTHORITIES_KEY = jwtProperty.getAuthoritiesKey();
        this.secret = jwtProperty.getSecret();
        this.tokenValidTime = jwtProperty.getTokenValidTime();
        this.header = jwtProperty.getHeader();

        this.keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    @Deprecated
    public String createJws(String user_no) throws Exception {
        Date now = new Date();
        Claims claims = Jwts.claims().setSubject("userPk");
        claims.put("user_no", user_no);
        claims.put("user_role", "USER");

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // (1)헤더타입 지정
                .setClaims(claims) // setClaims는 기존 PAYLOAD에 덮어쓰기된다. (즉, 뒤쪽에 놓게되면 이전내용이 무시됨)
                .setIssuer("dbsg_api") // (2)토큰발급자 설정
                .setIssuedAt(now) // (3)발급시간 설정(date)
                .setExpiration(new Date(now.getTime() + tokenValidTime)) // (4)만료시간 설정
                //.claim("id", "아이디") // (5)비공개 클레임 설정
                //.claim("email", "ajufresh@gmail.com")
                .signWith(key, SignatureAlgorithm.HS256) // (6)해상 알고리즘 및 시크릿KEY 지정
                .compact(); // 설정값을 바탕으로 JWT를 압축 (JWS형식)
    }

    public AuthResponse createAuthResponse(String uuid, MemberPermission permission) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiration = now.plusSeconds(tokenValidTime);
        Claims claims = Jwts.claims().setSubject("user");
        claims.put("uuid", uuid);
        claims.put("permission", permission.getName());

        String jws = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // (1)헤더타입 지정
                .setClaims(claims) // setClaims는 기존 PAYLOAD에 덮어쓰기된다. (즉, 뒤쪽에 놓게되면 이전내용이 무시됨)
                .setIssuer("dbsg") // (2)토큰발급자 설정
                .setIssuedAt(localDateTimeToDate(now))// (3)발급시간 설정(date)
                .setExpiration(localDateTimeToDate(expiration)) // (4)만료시간 설정
                //.claim("id", "아이디") // (5)비공개 클레임 설정
                //.claim("email", "ajufresh@gmail.com")
                .signWith(key, SignatureAlgorithm.HS256) // (6)해상 알고리즘 및 시크릿KEY 지정
                .compact(); // 설정값을 바탕으로 JWT를 압축 (JWS형식)

        return new AuthResponse(jws, expiration);
    }

    private static Date localDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    @Deprecated
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

    //Request Header에서 Token값 가져오기
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    //토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String token) {
        // TODO 비어있지 않을 때 오류가 나면 Exception 으로 구분 필요
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.debug("잘못된 JWT 서명입니다.");
            // throw new CustomException(ErrorCode.MALFRMED_JWT_EXCEPTION);
        } catch (ExpiredJwtException e) {
            log.debug("만료된 JWT 토큰입니다.");
            // throw new CustomException(ErrorCode.EXPIRED_JWT_EXCEPTION);
        } catch (UnsupportedJwtException e) {
            log.debug("지원되지 않는 JWT 토큰입니다.");
            // throw new CustomException(ErrorCode.UNSUPPORTED_JWT_EXCEPTION);
        } catch (IllegalArgumentException e) {
            log.debug("JWT 토큰이 잘못되었습니다.");
            // throw new CustomException(ErrorCode.ILLEGAL_JWT_EXCEPTION);
        } catch (NullPointerException e) {
            log.debug("토큰값이 비어있습니다.");
            // throw new CustomException(ErrorCode.NULL_POINTER_JWT_EXCEPTION);
        }
        return false;
    }

    public String validateHeader(String header) {  //deprecated
        if (header == null || !header.startsWith("Bearer ")) {
            //throw new IllegalArgumentException();
            return null;
        }
        return header.substring("Bearer ".length());
    }

    //토큰에서 Subject 추출
    public String getUserPk(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    public String getUserno(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get("user_no", String.class);
    }

    public String getUserRole(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get("user_role", String.class);
    }

    public String getUuid(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get("uuid", String.class);
    }

    public String getPermission(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get("permission", String.class);
    }

    // 발생가능예외
    //    UnsupportedJwtException : 예상하는 형식과 다른 형식이거나 구성의 JWT일 때
    //    MalformedJwtException : JWT가 올바르게 구서오디지 않았을 때
    //    ExpiredJwtException : JWT를 생성할 때 지정한 유효기간이 초과되었을 때
    //    SignatureException : JWT의 기존 서명을 확인하지 못했을 때
    //    IllegalArgumentException

    // 필터의 진행
    // HTTP 헤더에서 JWT 토큰값 가져오기
    // 유효한 토큰인지 확인 (baerer)
    // ㄴ 토큰에서 유저정보 불러오기
    // ㄴ SecurityContext 에 Authentication 객체를 저장합니다.

    //https://koogood.tistory.com/25
}
