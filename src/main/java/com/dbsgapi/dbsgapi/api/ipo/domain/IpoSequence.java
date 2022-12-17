package com.dbsgapi.dbsgapi.api.ipo.domain;

import com.dbsgapi.dbsgapi.global.response.CustomException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

import static com.dbsgapi.dbsgapi.global.response.ErrorCode.IPO_LIST_NOT_SUPPORTED_STATE;

@Getter
@AllArgsConstructor
/**
 * ipo 공개를 단계별로 표현한 enum
 */
public enum IpoSequence {
    // 사용되지 않음
    상장예비_심사청구(0, "상장예비_심사청구"),
    심사청구_승인(1, "심사청구 승인"),
    심사청구_탈락(1, "심사청구 탈락"),
    최초_증권신고서_제출(2, "최초 증권신고서 제출"),

    // 특별
    ALL(0, "전체"),
    TODAY(0, "오늘"),
    CANCEL(0, "청약철회"),
    SCHEDULE(0, "일정확인용"),

    // 실 사용
    BEFORE_FORECAST(2, "수요예측 이전"),
    NOW_FORECAST(3, "수요예측 진행중"),
    AFTER_FORECAST(4, "수요예측 완료"),

    BEFORE_IPO(4, "공모청약 진행전"),
    NOW_IPO(5, "공모청약 진행중"),
    AFTER_IPO(6, "공모청약 완료"),

    BEFORE_REFUND(6, "환불 진행 이전"),
    NOW_REFUND(7, "환불 진행 진행중"),
    AFTER_REFUND(8, "환불 진행 완료"),

    BEFORE_DEBUT(8, "상장 진행 이전"),
    NOW_DEBUT(9, "금일 상장 진행"),
    AFTER_DEBUT(10, "상장완료");
    private static final List<IpoSequence> notSupported = Arrays.asList(NOW_IPO, NOW_FORECAST, NOW_DEBUT, NOW_REFUND, SCHEDULE);

    private int level;
    private String sequence;

    public static void validate(IpoSequence ipoSequence) {
        validateSupported(ipoSequence);
    }

    public static void validateSupported(IpoSequence ipoSequence) {
        notSupported.stream()
                .filter(notSupportedSequence -> notSupportedSequence.equals(ipoSequence))
                .findAny()
                .ifPresent(e -> {
                    throw new CustomException(IPO_LIST_NOT_SUPPORTED_STATE);
                });
    }
}
