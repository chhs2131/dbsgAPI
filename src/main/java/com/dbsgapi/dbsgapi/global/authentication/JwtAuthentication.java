package com.dbsgapi.dbsgapi.global.authentication;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Collections;

@Slf4j
@AllArgsConstructor
public class JwtAuthentication implements Authentication {
    private final String token;
    private final String uuid;
    private final MemberPermission permission;
    private boolean isAuthenticated;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        log.debug("Getting authorities");
        if (isAuthenticated) {
            return Collections.singletonList(new SimpleGrantedAuthority(permission.getName()));
        }
        //return Collections.singletonList(new SimpleGrantedAuthority(Role.ANONYMOUS.getValue()));
        return Collections.singletonList(new SimpleGrantedAuthority("ANONYMOUS"));
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.isAuthenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        if (StringUtils.hasText(uuid) && isAuthenticated)
            return uuid;
        return null;
    }
}
