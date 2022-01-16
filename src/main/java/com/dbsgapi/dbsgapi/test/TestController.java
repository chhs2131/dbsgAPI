package com.dbsgapi.dbsgapi.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;

import java.util.Date;
import java.util.List;
import java.util.stream.DoubleStream;

@Slf4j
@Controller
public class TestController {
    @Autowired
    private TestService testService;

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
}
