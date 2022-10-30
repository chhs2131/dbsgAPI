package com.dbsgapi.dbsgapi.api.ipo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommentLevel {
    IPO_CANCEL(0, "청약이 철회되었습니다."),
    NEW(1, "신규 등록되었습니다."),
    SCHEDULE(2, "일정이 변경되었습니다."),
    DYNAMIC(3, "공모 정보가 업데이트 되었습니다."),
    STATIC(4, "기업 정보가 업데이트 되었습니다."),
    UNDERWRITER(5, "배정수량이 변경되었습니다."),
    ;
    private int level;
    private String name;
}
