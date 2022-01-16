package com.dbsgapi.dbsgapi.login.kakao.mapper;

import com.dbsgapi.dbsgapi.login.dto.MemberDto;
import com.dbsgapi.dbsgapi.login.kakao.dto.KakaoMemberDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface KakaoMapper {
    MemberDto selectMember(long userNo) throws Exception;
    int insertMember(MemberDto memberDto) throws Exception;
    KakaoMemberDto selectKakaoMember(String kakaoUid) throws Exception;
    void insertKakaoMember(KakaoMemberDto kakaoMemberDto) throws Exception;
}
