package com.dbsgapi.dbsgapi.api.ipo.dto;

import com.dbsgapi.dbsgapi.global.util.JsonCommentConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description ="종목에 대한 요약된 정보를 제공합니다. (IPO목록 및 스케쥴에서 사용)")
@Data
public class IpoSummaryDto {
    @Schema(description ="DB에서 관리하는 index번호", example = "100077", required=true)
    private long ipoIndex;
    @Schema(description ="종목명", example = "LG에너지솔루션")
    private String stockName;
    @Schema(description ="시장명", example = "코스피", allowableValues = {"코스피","코스닥","코넥스"}, required=true)
    private String stockExchange;
    @Schema(description ="유형구분", example = "공모주", allowableValues = {"공모주","실권주","스팩주"})
    private String stockKinds;

    @Schema(description ="수요예측 시작일", example = "2022-01-10")
    private String ipoForecastStart;
    @Schema(description ="수요예측 마감일", example = "2022-01-11")
    private String ipoForecastEnd;
    @Schema(description ="청약 시작일", example = "2022-01-18", required=true)
    private String ipoStartDate;
    @Schema(description ="청약 종료일", example = "2022-01-19")
    private String ipoEndDate;
    @Schema(description ="환불 및 배정일", example = "2022-01-21")
    private String ipoRefundDate;
    @Schema(description ="시장 상장일", example = "2022-01-27")
    private String ipoDebutDate;

    @Schema(description ="철회 여부", example = "Y")
    private String ipoCancelBool;
    @Schema(description ="철회 신고서 발행일", example = "2022-01-27")
    private String ipoCancelDate;

    private Integer ipoPriceLow;
    private Integer ipoPriceHigh;
    private Integer ipoPrice;

    @Schema(description ="주간사", example = "KB증권,대신증권,신한금융,미래에셋증권,신영증권,하나금융,하이투자")
    private String underwriter;
    @Schema(description ="태그", example = "")
    private String tag;

    // comment 관련 정보들 (최근 코멘트 기능 현재 사용하지 않음)
    private int recentCommentIndex;
    private String logType;
    private String comment;
    private String changeLogJson;
    @Schema(description ="최근 발행된 코멘트", example = "수요예측이 끝났습니다! 결과를 확인해보세요.")
    private String recentComment;

    public String getRecentComment() { return this.recentComment; }
    public void setComment(String comment) {
        this.comment = comment;
        this.recentComment = comment;
    }
    public void setChangeLogJson(String changeLogJson) {
        // changeLogJson이 comment보다 후순위로 값을 가져옴. (아마 mybatis에서 가져오는 순서대로인것 같음)
        this.changeLogJson = changeLogJson;

        // changeLogJson을 체크하여 null이 아닌경우 comment를 갱신함.
        if(changeLogJson != null && !changeLogJson.isEmpty()) {
            JsonCommentConverter jcc = new JsonCommentConverter();
            jcc.setCommentType(this.logType);
            jcc.setCommentJson(this.changeLogJson);
            this.recentComment = jcc.getRecentComment();
        }
    }

    @JsonIgnore
    public int getRecentCommentIndex() {  // 외부에 값을 표출하진 않음 jsonIgnore
        return recentCommentIndex;
    }
    @JsonIgnore
    public String getLogType() {  // 외부에 값을 표출하진 않음 jsonIgnore
        return logType;
    }
    @JsonIgnore
    public String getComment() {  // 외부에 값을 표출하진 않음 jsonIgnore
        return comment;
    }
    @JsonIgnore
    public String getChangeLogJson() {  // 외부에 값을 표출하진 않음 jsonIgnore
        return changeLogJson;
    }
}
