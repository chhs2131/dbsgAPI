package com.dbsgapi.dbsgapi.global.authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
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
                .orElseThrow(() -> new IllegalArgumentException("매칭X MemberPermission name " + name));
    }

    public static MemberPermission from(int id) {
        return Arrays.stream(MemberPermission.values())
                .filter(permission -> permission.getId() == id)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("매칭X MemberPermission id " + id));
    }
}
