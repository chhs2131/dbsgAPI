package com.dbsgapi.dbsgapi.global.authentication;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberPermissionTest {

    @Test
    void from() {
        MemberPermission memberPermission = MemberPermission.from("GUEST");
        assertEquals(1, memberPermission.getId());
    }

    @Test
    void testFrom() {
        MemberPermission memberPermission = MemberPermission.from(1);
        assertEquals("GUEST", memberPermission.getName());
    }
}