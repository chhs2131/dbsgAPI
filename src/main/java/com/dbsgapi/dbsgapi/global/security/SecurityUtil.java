package com.dbsgapi.dbsgapi.global.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Slf4j
public class SecurityUtil {
    private SecurityUtil() {
    }

    public static Optional<String> getCurrentUsername() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            log.debug("Security Context에 인증 정보가 없습니다.");
            return Optional.empty();
        } else {
            log.debug("인증객체 => " + authentication.toString());
        }

        String username = authentication.getName();
        log.debug("=> username : " + username);
        /*
        String username = null;
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
            log.debug("=> security principal : " + springSecurityUser.toString());
            username = springSecurityUser.getUsername();
        } else if (authentication.getPrincipal() instanceof String) {
            username = (String) authentication.getPrincipal();
        }
         */

        return Optional.ofNullable(username);
    }

    public static long getUserNo() {
        Optional<String> uuid = getCurrentUsername();
        return Long.parseLong(uuid.orElse("0"));
    }
}
