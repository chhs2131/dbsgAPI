package com.dbsgapi.dbsgapi.test;

import java.util.List;

public interface TestService {
    String TestService(String testWord) throws Exception;
    List<TestDto> TestSQL() throws Exception;
}
