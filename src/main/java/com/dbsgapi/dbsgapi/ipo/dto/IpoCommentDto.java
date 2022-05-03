package com.dbsgapi.dbsgapi.ipo.dto;

import com.dbsgapi.dbsgapi.common.JsonCommentConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class IpoCommentDto {
    private long commentIndex;
    private long ipoIndex;
    private String writer;
    private String stockName;
    private String comment;
    private String registDate;

    private String logType;
    private String changeLogJson;

    JsonCommentConverter jcc = new JsonCommentConverter();

    public void setChangeLogJson(String changeLogJson) {
        // changeLogJson이 comment보다 후순위로 값을 가져옴. (아마 mybatis에서 가져오는 순서대로인것 같음)
        this.changeLogJson = changeLogJson;

        // changeLogJson을 체크하여 null이 아닌경우 comment를 갱신함.
        if(changeLogJson != null && !changeLogJson.isEmpty()) {
            jcc.setCommentType(this.logType);
            jcc.setCommentJson(this.changeLogJson);
            this.comment = jcc.toString();
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
