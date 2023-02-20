package com.dbsgapi.dbsgapi.api.member.service;

import com.dbsgapi.dbsgapi.api.member.dto.MemberDto;

public interface MemberService {
    MemberDto findMemberByUuid(String uuid);
}
