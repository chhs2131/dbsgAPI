package com.dbsgapi.dbsgapi.security;

import javax.sql.DataSource;

import com.dbsgapi.dbsgapi.common.JwtUtil;
import com.dbsgapi.dbsgapi.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final JwtUtil jwtUtil = new JwtUtil();

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManagerBean();
    }
    // DB 객체값.
    //@Autowired
    //DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.debug("security config.....");
        // 웹페이지에 대해서는 사용하지않음 (REST만 고려함)
        http.httpBasic().disable();

        // 세션기능 사용하지않음
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // CSRF 예외처리 (토큰 없이도 동작 가능하게 하기)
        http.csrf().disable();

        // @RequestMapping 별 권한제어
        http.authorizeRequests()
                .antMatchers("/api/**").hasAnyRole("ADMIN", "USER", "GUEST")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll();

        http.addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        // 필터 추가
        //http.addFilterBefore(new JwtAuthen)

        // 기본 제공 로그인 페이지
        //http.formLogin().loginPage("/login/login.do");

        // 권한 없을때 띄울 페이지
        //http.exceptionHandling().accessDeniedPage("/board/openBoardList3.do");

        // 로그아웃시 지정 페이지로 이동
        //http.logout().logoutSuccessUrl("/board/openBoardList3.do").invalidateHttpSession(true);
    }

    // 계정 정보 확인 및 비교하여 등록된 사용자인지 확인
    /*
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        String query1 = "SELECT user_id username, CONCAT('{noop}', user_pass) password, "
                + " CASE WHEN enabled = 'Y' THEN true else false END enabled" + " FROM members WHERE user_id = ?";
        String query2 = "SELECT user_id, role_name role" + " FROM members WHERE user_id = ?";

        auth.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery(query1).rolePrefix("ROLE_")
                .authoritiesByUsernameQuery(query2);
    }
    */

    @Bean
    public SpringSecurityDialect springSecurityDialect() {
        return new SpringSecurityDialect();
    }
}
