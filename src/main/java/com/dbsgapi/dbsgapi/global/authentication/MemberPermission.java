package com.dbsgapi.dbsgapi.global.authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum MemberPermission {
    GUEST(1, "GUEST"),
    USER(2, "USER"),
    ADMIN(3, "ADMIN");

    private int id;
    private String name;


    public static MemberPermission from(String name) {
        return Arrays.stream(MemberPermission.values())
                .filter(permission -> permission.getName().equals(name))
                .findAny()
                .orElse(null);  //TODO orElseThrow 사용
    }
}
