package com.dbsgapi.dbsgapi.api.ipo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IpoComment {
    private CommentLevel type;
    private String comment;
    private String detail;

    @JsonIgnore
    public boolean isHaveTitle() {
        return !comment.isEmpty();
    }

    public static IpoComment makeNew(KindOfComment kind, String detail) {
        return IpoComment.builder()
                .type(kind.getType())
                .comment(kind.getComment())
                .detail(detail)
                .build();
    }
}
