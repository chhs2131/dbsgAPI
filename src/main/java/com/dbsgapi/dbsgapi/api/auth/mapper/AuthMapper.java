package com.dbsgapi.dbsgapi.api.auth.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthMapper {
    void createMember();
    void getMemberByUuid();
    void getMemberBySocialAccount();
    void updateMemberNameByUuid();
    void deleteMemberByUuid();
}
