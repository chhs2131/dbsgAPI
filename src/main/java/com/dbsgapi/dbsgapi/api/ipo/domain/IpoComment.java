package com.dbsgapi.dbsgapi.api.ipo.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IpoComment {
    private CommentLevel type;
    private String comment;
    private String detail;

    public static IpoComment makeNew(KindOfComment kind, String detail) {
        return IpoComment.builder()
                .type(kind.getType())
                .comment(kind.getComment())
                .detail(detail)
                .build();
    }
}
