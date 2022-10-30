package com.dbsgapi.dbsgapi.api.ipo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.dbsgapi.dbsgapi.api.ipo.domain.CommentLevel.*;

@Getter
@AllArgsConstructor
public enum KindOfComment {  // state
    CANCEL(IPO_CANCEL, "공모청약이 철회되었습니다."),
    CHANGED_IPO_DATE(SCHEDULE, "공모청약일이 변경되었습니다."),
    //CHANGED_START_DATE(1, "공모청약 시작일이 변경되었습니다."),
    //CHANGED_END_DATE(1, "공모청약 종료일이 변경되었습니다."),
    CHANGED_REFUND_DATE(SCHEDULE, "대금 환불일이 변경되었습니다."),
    CHANGED_DEBUT_DATE(SCHEDULE, "상장일이 변경되었습니다."),
    CHANGED_FORECAST_DATE(SCHEDULE, "수요예측일이 변경되었습니다."),
    //CHANGED_FORECAST_START(1, "수요예측 시작일이 변경되었습니다."),
    //CHANGED_FORECAST_END(1, "수요예측 종료일이 변경되었습니다."),
    CHANGED_EX_DATE(SCHEDULE, "구주주 청약일이 변경되었습니다."),
    //CHANGED_EX_START(1, "구주주 청약 시작일이 변경되었습니다."),
    //CHANGED_EX_END(1, "구주주 청약 종료일이 변경되었습니다."),

    CHANGED_LOCK_UP_PERCENT(DYNAMIC, "의무보유확약 비율이 정정되었습니다."),
    SET_ACCEPTANCE_RATE(DYNAMIC, "수요 예측 결과가 발표되었습니다."),
    SET_IPO_PRICE(DYNAMIC, "공모가가 확정되었습니다."),
    CHANGED_IPO_PRICE_BAND(DYNAMIC, "공모가 밴드가 변경되었습니다."),
    CHANGED_IPO_MIN_DEPOSIT(DYNAMIC, "최소 청약 증거금이 변경되었습니다."),
    CHANGED_PUT_BACK_OPTION(DYNAMIC, "환매청구권 정보가 변경되었습니다."),

    CHANGED_PROFITS(STATIC, "영업이익 금액이 정정되었습니다."),
    CHANGED_SALES(STATIC, "매출액 금액이 정정되었습니다."),
    CHANGED_IPO_SHARES(STATIC, "총 공모 주식수가 변경되었습니다."),
    CHANGED_PAR_VALUE(STATIC, "액면가가 변경되었습니다."),
    CHANGED_PURPOSE_OF_FUNDS(STATIC, "자금의 사용목적이 변경되었습니다."),

    CHANGED_UNDERWRITER_TOTAL(UNDERWRITER, "청약가능 수량이 변경되었습니다."),
    CHANGED_UNDERWRITER_MIN_QUAN(UNDERWRITER, "최소 청약 수량이 변경되었습니다."),
    CHANGED_UNDERWRITER_DEPOSIT_PERCENT(UNDERWRITER, "청약 증거금 비율이 변경되었습니다."),
    ;
    private CommentLevel type;
    private String comment;
}
