package com.dbsgapi.dbsgapi.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {  // CORS 처리
        registry.addMapping("/**").allowedOrigins("http://localhost:3000");  // 헤로쿠 서버
    }
}
