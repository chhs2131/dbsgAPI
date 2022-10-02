package com.dbsgapi.dbsgapi.global.response.handler;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Arrays;

@Slf4j
@Getter
@RestControllerAdvice
public class RestResponseHandler implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        String classPath = returnType.getContainingClass().toString();
        if(classPath.startsWith("class com.dbsgapi.dbsgapi.api")) {
            return true;
        }

        log.debug("\n[ResponseBodyAdvice Supports]"
                + "\nPackagePath1: " + returnType.getContainingClass()
                + "\nPackagePath2: " + returnType.getExecutable().getDeclaringClass().toString()
                + "\nclassName: " + returnType.getExecutable().getName()
                + "\nParameterType: " + returnType.getParameterType()
                + "\nParameterName: " + returnType.getParameterName()
                + "\nDeclaredAnnotations: " + Arrays.toString(returnType.getExecutable().getDeclaredAnnotations())
        );
        return false;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        log.debug("형형 이거해줘");
        return body;
    }
}
