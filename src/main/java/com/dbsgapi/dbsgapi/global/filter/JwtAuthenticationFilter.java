package com.dbsgapi.dbsgapi.global.filter;

import com.dbsgapi.dbsgapi.api.login.dto.MemberDto;
import com.dbsgapi.dbsgapi.global.authentication.AnonymousAuthentication;
import com.dbsgapi.dbsgapi.global.authentication.JwtAuthentication;
import com.dbsgapi.dbsgapi.global.authentication.MemberPermission;
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

        // 유효한 토크인지 확인
        if (StringUtils.hasText(token) && jwtUtil.validateToken(token)) {
            String uuid = jwtUtil.getUuid(token);
            MemberPermission permission = MemberPermission.from(jwtUtil.getPermission(token));
            //TODO Permission에 대한 검증이 필요할까?

            // SecurityContext 에 Authentication 객체를 저장
            this.setAuthentication(token, uuid, permission);
            log.debug("Security Context에 '{}' 인증 정보를 저장했습니다. URI: {}", SecurityContextHolder.getContext().getAuthentication().getName(), requestUri);
        } else {
            this.setAnonymousAuthentication();
            log.debug("유효한 JWT 토큰이 없습니다. URI: {}", requestUri);
        }

        filterChain.doFilter(request, response);
    }

    private static void setAuthentication(String token, String uuid, MemberPermission permission) {
        JwtAuthentication authentication = new JwtAuthentication(token, uuid, permission, true);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void setAnonymousAuthentication() {
        AnonymousAuthentication anonymousAuthentication = new AnonymousAuthentication();
        SecurityContextHolder.getContext().setAuthentication(anonymousAuthentication);
    }

}
