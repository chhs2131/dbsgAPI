package com.dbsgapi.dbsgapi.member.dto;

import lombok.Data;

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
