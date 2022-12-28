package com.dbsgapi.dbsgapi.global.authentication;

import com.dbsgapi.dbsgapi.api.login.dto.MemberDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;

@Slf4j
public class JwtAuthentication implements Authentication {

    private final String token;
    private long userNo = 0;
    private String role;
    private boolean isAuthenticated;

    public JwtAuthentication(MemberDto dto) {
        this.token = dto.getJwt();
        this.userNo = dto.getUserNo();
        this.role = dto.getRoleName();
        this.isAuthenticated = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        log.debug("Getting authorities");
        if (isAuthenticated) {
            return Collections.singletonList(new SimpleGrantedAuthority(role));
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
        if (userNo != 0 && isAuthenticated)
            return Long.toString(userNo);
        return null;
    }
}
