package com.dbsgapi.dbsgapi.global.util;

import com.dbsgapi.dbsgapi.global.configuration.properties.type.ApiProvider;
import com.dbsgapi.dbsgapi.global.configuration.properties.type.ApiRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class WebClientUtil {
    private static final WebClient webClient = WebClient.builder()
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();

    private WebClient newBaseWebClient(ApiProvider apiProvider) {
        return webClient.mutate()
                .baseUrl(apiProvider.getBaseUrl())
                .build();
    }

    public WebClient.RequestBodySpec newRequestWebClient(ApiProvider apiProvider, ApiRequest apiRequest) {
        return newBaseWebClient(apiProvider)
                .method(apiRequest.getMethod())
                .uri(apiRequest.getPath());
                //.headers();
    }

    public static void newResponseWebClient(ApiProvider apiProvider, ApiRequest apiRequest) {

    }
}
