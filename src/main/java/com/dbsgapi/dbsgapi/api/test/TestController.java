package com.dbsgapi.dbsgapi.api.test;

import com.dbsgapi.dbsgapi.global.security.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.dbsgapi.dbsgapi.global.common.JwtUtil;

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

        Date date_now = new Date(System.currentTimeMillis());// 년월일시분초 14자리 포멧
        SimpleDateFormat fourteen_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return "test.ServerTime: " + date_now;
    }

    @GetMapping("/testPage")
    public ModelAndView testPage() throws Exception {
        ModelAndView mv = new ModelAndView("/test/testPage");
        return mv;
    }

    @GetMapping("/testLayout")
    public ModelAndView testLayout() throws Exception {
        ModelAndView mv = new ModelAndView("/test/layout_test/main/index");
        return mv;
    }

    @GetMapping("/testLayoutPage")
    public ModelAndView testLayoutPage() throws Exception {
        ModelAndView mv = new ModelAndView("/admin/layout/default_layout");
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

    @GetMapping(path = "/testHeader", headers = "HEADER")
    public ResponseEntity<String> messageForHeader(@RequestHeader HttpHeaders header) {
        // https://mungto.tistory.com/440
        // HEADER 이라는 헤더KEY를 추가하면 해당 헤더에있는 KEY를 반환한다.
        return ResponseEntity.ok().body(header.getFirst("HEADER") + " .. [header body]" + header.toString());
    }

    @GetMapping("/user")
    public ResponseEntity<Optional<String>> getMyUserInfo(HttpServletRequest request) {
        // Token을 통해 유저이름과 닉네임, 권한을 조회하여 반환 (단, 본인한정)
        log.debug("user실행 => " + request.toString());
        return ResponseEntity.ok(SecurityUtil.getCurrentUsername());
    }
}
