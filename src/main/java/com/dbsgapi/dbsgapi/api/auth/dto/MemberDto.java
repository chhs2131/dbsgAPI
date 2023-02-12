package com.dbsgapi.dbsgapi.api.auth.dto;

import com.dbsgapi.dbsgapi.global.authentication.MemberPermission;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class MemberDto {
    private String uuid;
    private MemberPermission memberPermission;
    private String name;
    private LocalDateTime registDatetime;
    private LocalDateTime updateDatetime;

    public void setMemberPermission(String name) {
        memberPermission = MemberPermission.from(name);
    }
}
