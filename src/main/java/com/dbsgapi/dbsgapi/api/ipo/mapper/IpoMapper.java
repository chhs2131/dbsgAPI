package com.dbsgapi.dbsgapi.api.ipo.mapper;

import com.dbsgapi.dbsgapi.api.ipo.dto.IpoCommentDto;
import com.dbsgapi.dbsgapi.api.ipo.dto.IpoDto;
import com.dbsgapi.dbsgapi.api.ipo.dto.IpoSummaryDto;
import com.dbsgapi.dbsgapi.api.ipo.dto.IpoUnderwriterDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface IpoMapper {
    List<IpoSummaryDto> selectIpos(Map<String, Object> map) throws Exception;

    IpoDto selectIpo(long ipoIndex) throws Exception;

    List<IpoSummaryDto> selectIpoScheduleList(Map<String, String> map) throws Exception;

    List<IpoCommentDto> selectIpoComment(long ipoIndex) throws Exception;

    IpoCommentDto selectIpoCommentIndex(long commentIndex) throws Exception;

    List<IpoCommentDto> selectIpoCommentList(Map<String, String> map) throws Exception;

    List<IpoUnderwriterDto> selectIpoUnderwriter(long ipoIndex) throws Exception;
}
