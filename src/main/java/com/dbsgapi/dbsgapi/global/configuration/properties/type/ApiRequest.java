package com.dbsgapi.dbsgapi.global.configuration.properties.type;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;

@Getter
@RequiredArgsConstructor
public class ApiRequest {
    private final String path;
    private final HttpMethod method;
}
