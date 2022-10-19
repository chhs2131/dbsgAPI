package com.dbsgapi.dbsgapi.api.ipo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
/**
 * ipo 공개를 단계별로 표현한 enum
 */
public enum IpoSequence {
    ALL,
    TODAY,
    CANCEL,
    BEFORE_FORECAST,
    NOW_FORECAST,
    AFTER_FORECAST,
    BEFORE_IPO,
    NOW_IPO,
    AFTER_IPO,
    BEFORE_REFUND,
    NOW_REFUND,
    AFTER_REFUND,
    BEFORE_DEBUT,
    NOW_DEBUT,
    AFTER_DEBUT;
    /*
    // 사용되지 않음
    상장예비_심사청구(0,"상장예비_심사청구"),
    심사청구_승인(1, "심사청구 승인"),
    심사청구_탈락(1,"심사청구 탈락"),
    최초_증권신고서_제출(2, "최초 증권신고서 제출"),

    // 실 사용
    BEFORE_FORECAST(2,"수요예측 이전"),
    NOW_FORECAST(3,"수요예측 진행중"),
    AFTER_FORECAST(4,"수요예측 완료"),

    BEFORE_IPO(4, "공모청약 진행전"),
    NOW_IPO(5, "공모청약 진행중"),
    AFTER_IPO(6, "공모청약 완료"),

    BEFORE_REFUND(6, "환불 진행 이전"),
    NOW_REFUND(7, "환불 진행 진행중"),
    AFTER_REFUND(8, "환불 진행 완료"),

    BEFORE_DEBUT(8, "상장 진행 이전"),
    NOW_DEBUT(9, "금일 상장 진행"),
    AFTER_DEBUT(10, "상장완료")
    ;
    private int level;
    private String sequence;
    */
}
