package com.dbsgapi.dbsgapi.ipo.service;

import com.dbsgapi.dbsgapi.ipo.dto.IpoDto;
import com.dbsgapi.dbsgapi.ipo.dto.IpoSummaryDto;

import java.util.List;

public interface IpoService {
    List<IpoSummaryDto> selectIpos() throws Exception;
}
