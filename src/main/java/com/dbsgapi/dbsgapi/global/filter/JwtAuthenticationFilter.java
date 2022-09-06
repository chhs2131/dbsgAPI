package com.dbsgapi.dbsgapi.global.filter;

import com.dbsgapi.dbsgapi.global.common.JwtUtil;
import com.dbsgapi.dbsgapi.api.login.dto.MemberDto;
import com.dbsgapi.dbsgapi.global.security.authentication.JwtAuthentication;
import com.dbsgapi.dbsgapi.global.security.authentication.AnonymousAuthentication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
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
        String authHeader = jwtUtil.resolveToken((HttpServletRequest) request);
        String requestUri = ((HttpServletRequest) request).getRequestURI();
        String token = jwtUtil.validateHeader(authHeader);

        log.debug("authHeader : " + authHeader);
        log.debug("JWT : " + token);

        // 유효한 토크인지 확인
        if(StringUtils.hasText(token) && jwtUtil.validateToken(token)) {
            String userNo = jwtUtil.getUserno(token);
            String userRole = jwtUtil.getUserRole(token);
            MemberDto memberDto = new MemberDto();
            memberDto.setForJwt(userNo, userRole, token);

            // SecurityContext 에 Authentication 객체를 저장
            this.setAuthentication(memberDto);
            log.debug("Security Context에 '{}' 인증 정보를 저장했습니다. URI: {}", SecurityContextHolder.getContext().getAuthentication().getName(), requestUri);
        } else {
            this.setAnonymousAuthentication();
            log.debug("유효한 JWT 토큰이 없습니다. URI: {}", requestUri);
        }

        /*

        try {
            String authHeader = jwtUtil.resolveToken((HttpServletRequest) request);
            String requestUri = ((HttpServletRequest) request).getRequestURI();
            String token = jwtUtil.validateHeader(authHeader);

            log.debug("authHeader : " + authHeader);
            log.debug("JWT : " + token);

            // 유효한 토크인지 확인
            if(StringUtils.hasText(token) && jwtUtil.validateToken(token)) {
                // 유저정보를 받아옴
                Authentication authentication = jwtUtil.getAuthentication(token);
                // SecurityContext 에 Authentication 객체를 저장
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.debug("Security Context에 '{}' 인증 정보를 저장했습니다. URI: {}", authentication.getName(), requestUri);
            } else {
                log.debug("유효한 JWT 토큰이 없습니다. URI: {}", requestUri);
            }
        }
        catch (Exception e) {
            log.debug(e.toString());
        }
         */

        chain.doFilter(request, response);
    }

    private void setAuthentication(MemberDto dto) {
        if (!dto.getJwt().isEmpty()) {
            JwtAuthentication authentication = new JwtAuthentication(dto);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }

    private void setAnonymousAuthentication() {
        AnonymousAuthentication anonymousAuthentication = new AnonymousAuthentication();
        SecurityContextHolder.getContext().setAuthentication(anonymousAuthentication);
    }
}
