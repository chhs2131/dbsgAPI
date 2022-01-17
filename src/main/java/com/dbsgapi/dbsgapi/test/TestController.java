package com.dbsgapi.dbsgapi.test;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import com.dbsgapi.dbsgapi.common.JwtUtil;

@Slf4j
@Controller
public class TestController {
    @Autowired
    private TestService testService;

    JwtUtil jwtUtil = new JwtUtil();

    @GetMapping("/test")
    @ResponseBody
    public String testString() throws Exception {
        log.trace("Trace");
        log.debug("Debug 디버깅용 로그");
        log.info("Info 정보성 로그");
        log.warn("Warnning 경고");
        log.error("Error 에러");
        return "test";
    }

    @GetMapping("/testPage")
    public ModelAndView testPage() throws Exception {
        ModelAndView mv = new ModelAndView("/test/testPage");
        return mv;
    }

    @GetMapping("/testService")
    @ResponseBody
    public String testService() throws Exception {
        return testService.TestService("ServiceInput");
    }

    @GetMapping("/testSQL")
    @ResponseBody
    public List<TestDto> testSQL() throws Exception {
        List<TestDto> returnValue = testService.TestSQL();
        System.out.println(returnValue);
        return returnValue;
    }

    @GetMapping("/testJWT")
    @ResponseBody
    public String makeJwtToken() throws Exception {
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String jws = Jwts.builder().setSubject("Joe").signWith(key).compact();
        return jws;
    }

    @GetMapping("/testJWT2")
    @ResponseBody
    public String makeJwtToken2() throws Exception {
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        Date now = new Date();

        String jws = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // (1)헤더타입 지정
                .setIssuer("fresh") // (2)토큰발급자 설정
                .setIssuedAt(now) // (3)발급시간 설정(date)
                .setExpiration(new Date(now.getTime() + Duration.ofMinutes(30).toMillis())) // (4)만료시간 설정
                .claim("id", "아이디") // (5)비공개 클레임 설정
                .claim("email", "ajufresh@gmail.com")
                .signWith(key) // (6)해상 알고리즘 및 시크릿KEY 지정
                .compact(); // 설정값을 바탕으로 JWT를 압축 (JWS형식)


        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jws);
            //OK, we can trust this JWT
            return "[good jws] " + jws;
        } catch (JwtException e) {
            //don't trust the JWT!
            return "[bad jws] " + jws + "[Error] " + e;
        }
    }

    @GetMapping("/testJWT3")
    @ResponseBody
    public String makeJwtToken2(String compactJws) throws Exception {
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(compactJws);
            //OK, we can trust this JWT
            return "[good jws] " + compactJws;
        } catch (JwtException e) {
            //don't trust the JWT!
            return "[bad jws] " + compactJws + "[Error] " + e;
        }
    }

    @GetMapping("/testJWTa")
    @ResponseBody
    public String makeJwtTokena() throws Exception {
        //jws 발급받기
        return jwtUtil.createJws();
    }

    @GetMapping("/testJWTb")
    @ResponseBody
    public String makeJwtTokenb(String jws) throws Exception {
        //jws 값 정상인지 판단하기
        return jwtUtil.accessJws(jws);
    }
}
