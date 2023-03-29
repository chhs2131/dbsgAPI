package com.dbsgapi.dbsgapi.global.configuration.properties.type;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public abstract class ApiProvider {
    private String key;
    private String baseUrl;
}
