package com.dbsgapi.dbsgapi.api.ipo.dto;

import com.dbsgapi.dbsgapi.api.ipo.domain.IpoComment;
import com.dbsgapi.dbsgapi.global.util.JsonCommentConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class IpoCommentDto {
    @Schema(example = "123")
    private long commentIndex;
    @Schema(example = "72")
    private long ipoIndex;
    private String writer;
    @Schema(description ="종목명", example = "LG에너지솔루션")
    private String stockName;
    @Schema(description ="유형구분", example = "공모주", allowableValues = {"공모주","실권주","스팩주"})
    private String stockKinds;
    @Schema(description ="제목", example = "공모 정보가 변경되었습니다.")
    private String title;
    @Schema(description ="내용")
    private List<IpoComment> commentList;
    @Schema(description ="등록일", example = "2022-01-01")
    private String registDate;

    private String logType;
    private String changeLogJson;

    public void setChangeLogJson(String changeLogJson) {
        // changeLogJson이 comment보다 후순위로 값을 가져옴. (아마 mybatis에서 가져오는 순서대로인것 같음)
        this.changeLogJson = changeLogJson;

        // changeLogJson을 체크하여 null이 아닌경우 comment를 갱신함.
        if(changeLogJson != null && !changeLogJson.isEmpty()) {
            JsonCommentConverter jcc = new JsonCommentConverter();
            jcc.setCommentType(this.logType);
            jcc.setCommentJson(this.changeLogJson);
            this.title = jcc.getRecentComment();
            this.commentList = jcc.getCommentList();
        }
    }
    @JsonIgnore
    public String getLogType() {  // 외부에 값을 표출하진 않음 jsonIgnore
        return logType;
    }
    @JsonIgnore
    public String getChangeLogJson() {
        return changeLogJson;
    }
}
