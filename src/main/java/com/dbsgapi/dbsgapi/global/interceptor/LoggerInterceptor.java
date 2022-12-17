package com.dbsgapi.dbsgapi.global.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class LoggerInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        log.info("======================== START ========================");
        log.info(" Class       \t:  " + handler.getClass());
        log.info(" Request URI \t:  " + request.getRequestURI());
        log.info(" Servlet URI \t:  " + request.getServletPath());
        return super.preHandle(request, response, handler);
    }

    //controller 처리 이후 이벤트 작동
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        log.info("======================== END ========================");
    }
}
