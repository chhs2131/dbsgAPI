package com.dbsgapi.dbsgapi.api.test;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService{
    private final TestMapper testMapper;

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
