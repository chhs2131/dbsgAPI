package com.dbsgapi.dbsgapi.test;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import javax.servlet.http.HttpServletRequest;

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

    @GetMapping("/testJWTa")
    @ResponseBody
    public String makeJwtTokena() throws Exception {
        //jws 발급받기
        return jwtUtil.createJws("123456");
    }

    @GetMapping("/testJWTb")
    @ResponseBody
    public String makeJwtTokenb(String jws) throws Exception {
        //jws 값 정상인지 판단하기
        String token = jwtUtil.validateHeader(jws);
        return jwtUtil.accessJws(token);
    }

    @GetMapping("/testJWTc")
    @ResponseBody
    public boolean makeJwtTokenc(String jws) throws Exception {
        //jws 값 정상인지 판단하기
        return jwtUtil.validateToken(jws);
    }

    @GetMapping("/testResponse")
    public ResponseEntity responseEntity() throws Exception {
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/testResponseString")
    public ResponseEntity<String> responseEntity2() throws Exception {
        return new ResponseEntity<String>("HelloWorld!",HttpStatus.OK);
    }
}
