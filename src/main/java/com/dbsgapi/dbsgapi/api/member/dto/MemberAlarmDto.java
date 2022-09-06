package com.dbsgapi.dbsgapi.api.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description ="알람에 대한 정보를 저장합니다.")
@Data
public class MemberAlarmDto {
    private long userNo;
    private long index;
    private String kind;
    private long kindNo;
    private String option1Enabled;
    private String option2Enabled;
    private String option3Enabled;
    private String option4Enabled;
    private String option5Enabled;
}
