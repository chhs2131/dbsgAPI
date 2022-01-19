package com.dbsgapi.dbsgapi.ipo.mapper;

import com.dbsgapi.dbsgapi.ipo.dto.IpoDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IpoMapper {
    List<IpoDto> selectIpos() throws Exception;
}
