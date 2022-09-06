package com.dbsgapi.dbsgapi.api.login.mapper;

import com.dbsgapi.dbsgapi.api.login.dto.MemberDto;
import com.dbsgapi.dbsgapi.api.login.dto.KakaoMemberDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface KakaoMapper {
    MemberDto selectMember(long userNo) throws Exception;
    int insertMember(MemberDto memberDto) throws Exception;
    KakaoMemberDto selectKakaoMember(String kakaoUid) throws Exception;
    void insertKakaoMember(KakaoMemberDto kakaoMemberDto) throws Exception;
}
