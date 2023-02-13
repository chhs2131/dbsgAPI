package com.dbsgapi.dbsgapi.api.auth.mapper;

import com.dbsgapi.dbsgapi.api.auth.dto.MemberDto;
import com.dbsgapi.dbsgapi.api.auth.dto.MemberOauthAccountDto;
import com.dbsgapi.dbsgapi.global.authentication.OauthType;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthMapper {
    void createMember(MemberDto member);
    MemberDto getMemberByUuid(String uuid);
    MemberDto getMemberByOauthAccount(MemberOauthAccountDto memberOauthAccount);
    void updateMember(MemberDto member);
    void deleteMember(MemberDto member);
    void deleteMemberByUuid(String uuid);
    void createOauthAccount(MemberOauthAccountDto memberOauthAccount);
}
