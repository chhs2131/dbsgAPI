package com.dbsgapi.dbsgapi.api.member.mapper;

import com.dbsgapi.dbsgapi.api.member.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface MemberMapper {
    void createMember(MemberDto member);
    Optional<MemberDto> getMemberByUuid(String uuid);
    void updateMember(MemberDto member);
    void deleteMember(MemberDto member);
    void deleteMemberByUuid(String uuid);
}
