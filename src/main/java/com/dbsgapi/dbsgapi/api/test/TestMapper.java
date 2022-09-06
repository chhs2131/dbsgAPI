package com.dbsgapi.dbsgapi.api.test;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TestMapper {
    List<TestDto> selectMembers() throws Exception;
}
