package com.dbsgapi.dbsgapi.filter;

import com.dbsgapi.dbsgapi.common.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends GenericFilterBean {
    private final JwtUtil jwtUtil = new JwtUtil();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // Header에서 유효 JWT 값을 가져오기
        String token = null;
        try {
            String authHeader = jwtUtil.resolveToken((HttpServletRequest) request);
            log.debug("authHeader : " + authHeader);
            token = jwtUtil.validateHeader(authHeader);
            log.debug("JWT : " + token);

            // 유효한 토크인지 확인
            if(token != null && jwtUtil.validateToken(token)) {
                // 유저정보를 받아옴
                Authentication authentication = jwtUtil.getAuthentication(token);
                // SecurityContext 에 Authentication 객체를 저장
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        catch (Exception e) {
        }
        chain.doFilter(request, response);
    }
}
