package com.dbsgapi.dbsgapi.ipo.service;

import com.dbsgapi.dbsgapi.ipo.dto.IpoDto;

import java.util.List;

public interface IpoService {
    List<IpoDto> selectIpos() throws Exception;
}
