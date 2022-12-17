package com.dbsgapi.dbsgapi.global.filter;

import com.dbsgapi.dbsgapi.api.login.dto.MemberDto;
import com.dbsgapi.dbsgapi.global.authentication.AnonymousAuthentication;
import com.dbsgapi.dbsgapi.global.authentication.JwtAuthentication;
import com.dbsgapi.dbsgapi.global.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Header에서 유효 JWT 값을 가져오기
        String authHeader = jwtUtil.resolveToken(request);
        String requestUri = (request).getRequestURI();
        String token = jwtUtil.validateHeader(authHeader);

        log.debug("authHeader : " + authHeader);
        log.debug("JWT : " + token);

        // 유효한 토크인지 확인
        if (StringUtils.hasText(token) && jwtUtil.validateToken(token)) {
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

        filterChain.doFilter(request, response);
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
