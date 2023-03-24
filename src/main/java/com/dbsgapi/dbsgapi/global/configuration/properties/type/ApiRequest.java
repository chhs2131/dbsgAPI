package com.dbsgapi.dbsgapi.global.configuration.properties.type;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ApiRequest {
    private final String path;
    private final String method;
}
