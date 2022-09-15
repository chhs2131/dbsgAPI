package com.dbsgapi.dbsgapi.global.configuration;

import com.dbsgapi.dbsgapi.global.configuration.properties.JwtProperty;
import com.dbsgapi.dbsgapi.global.configuration.properties.SocialProperty;
import com.dbsgapi.dbsgapi.global.configuration.properties.SwaggerProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = {SwaggerProperty.class, JwtProperty.class, SocialProperty.class})
public class PropertiesConfiguration {
    // ConstructorBinding 을 이용해서 등록할 Properties 클래스를 명시
}
