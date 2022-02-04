package com.dbsgapi.dbsgapi.ipo.mapper;

import com.dbsgapi.dbsgapi.ipo.dto.IpoDto;
import com.dbsgapi.dbsgapi.ipo.dto.IpoSummaryDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IpoMapper {
    List<IpoSummaryDto> selectIpos() throws Exception;
    // List<IpoDto> selectIpos() throws Exception;
}
