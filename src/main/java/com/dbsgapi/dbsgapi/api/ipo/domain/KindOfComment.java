package com.dbsgapi.dbsgapi.api.ipo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum KindOfComment {  // state
    CANCEL(0, "공모청약이 철회되었습니다."),
    CHANGED_IPO_DATE(1, "공모청약일이 변경되었습니다."),
    //CHANGED_START_DATE(1, "공모청약 시작일이 변경되었습니다."),
    //CHANGED_END_DATE(1, "공모청약 종료일이 변경되었습니다."),
    CHANGED_REFUND_DATE(1, "대금 환불일이 변경되었습니다."),
    CHANGED_DEBUT_DATE(1, "상장일이 변경되었습니다."),
    CHANGED_FORECAST_DATE(1, "수요예측일이 변경되었습니다."),
    //CHANGED_FORECAST_START(1, "수요예측 시작일이 변경되었습니다."),
    //CHANGED_FORECAST_END(1, "수요예측 종료일이 변경되었습니다."),
    CHANGED_EX_DATE(1, "구주주 청약일이 변경되었습니다."),
    //CHANGED_EX_START(1, "구주주 청약 시작일이 변경되었습니다."),
    //CHANGED_EX_END(1, "구주주 청약 종료일이 변경되었습니다."),

    CHANGED_LOCK_UP_PERCENT(2, "의무보유확약 비율이 정정되었습니다."),
    SET_ACCEPTANCE_RATE(2, "수요 예측 결과가 발표되었습니다."),
    SET_IPO_PRICE(2, "공모가가 확정되었습니다."),
    CHANGED_IPO_PRICE_BAND(2, "공모가 밴드가 변경되었습니다."),
    CHANGED_IPO_MIN_DEPOSIT(2, "최소 청약 증거금이 변경되었습니다."),
    CHANGED_PUT_BACK_OPTION(2, "환매청구권 정보가 변경되었습니다."),

    CHANGED_PROFITS(3, "영업이익 금액이 정정되었습니다."),
    CHANGED_SALES(3, "매출액 금액이 정정되었습니다."),
    CHANGED_IPO_SHARES(3, "총 공모 주식수가 변경되었습니다."),
    CHANGED_PAR_VALUE(3, "액면가가 변경되었습니다."),
    CHANGED_PURPOSE_OF_FUNDS(3, "자금의 사용목적이 변경되었습니다."),

    CHANGED_UNDERWRITER_TOTAL(4, "청약가능 수량이 변경되었습니다."),
    CHANGED_UNDERWRITER_MIN_QUAN(4, "최소 청약 수량이 변경되었습니다."),
    CHANGED_UNDERWRITER_DEPOSIT_PERCENT(4, "청약 증거금 비율이 변경되었습니다."),
    ;
    private int level;
    private String comment;
}
