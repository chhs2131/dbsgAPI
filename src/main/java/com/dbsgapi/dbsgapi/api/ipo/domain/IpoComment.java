package com.dbsgapi.dbsgapi.api.ipo.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IpoComment {
    private KindOfComment kind;
    private String detail;

    public static IpoComment makeNew(KindOfComment kind, String detail) {
        return IpoComment.builder()
                .kind(kind)
                .detail(detail)
                .build();
    }
}
