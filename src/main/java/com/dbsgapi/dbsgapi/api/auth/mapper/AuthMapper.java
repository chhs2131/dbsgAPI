package com.dbsgapi.dbsgapi.api.auth.mapper;

import com.dbsgapi.dbsgapi.api.member.dto.MemberDto;
import com.dbsgapi.dbsgapi.api.auth.dto.MemberOauthAccountDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthMapper {
    MemberDto getMemberByOauthAccount(MemberOauthAccountDto memberOauthAccount);
    void createOauthAccount(MemberOauthAccountDto memberOauthAccount);
}
