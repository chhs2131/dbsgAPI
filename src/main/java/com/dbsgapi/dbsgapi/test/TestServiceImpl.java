package com.dbsgapi.dbsgapi.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestServiceImpl implements TestService{
    @Autowired
    private TestMapper testMapper;

    @Override
    public String TestService(String testWord) throws Exception {
        return "returnTestService" + testWord;
    }

    @Override
    public List<TestDto> TestSQL() throws Exception {
        System.out.println("TestSQL 서비스 실행");
        return testMapper.selectMembers();
    }


}
