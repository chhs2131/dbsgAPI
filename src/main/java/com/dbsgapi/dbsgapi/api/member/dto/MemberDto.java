package com.dbsgapi.dbsgapi.api.member.dto;

import com.dbsgapi.dbsgapi.global.authentication.MemberPermission;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

@Slf4j
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {
    private String uuid;
    private MemberPermission memberPermission;
    private String name;
    private LocalDateTime registDatetime;
    @Nullable
    private LocalDateTime updateDatetime;

    public void setMemberPermission(int id) {
        memberPermission = MemberPermission.from(id);
    }

    public String getMemberPermission() {
        return memberPermission.getName();
    }

    @JsonIgnore
    public MemberPermission getMemberPermissionType() {
        return memberPermission;
    }
}
