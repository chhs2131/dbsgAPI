package com.dbsgapi.dbsgapi.ipo.mapper;

import com.dbsgapi.dbsgapi.ipo.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface IpoMapper {
    List<IpoSummaryDto> selectIpos() throws Exception;
    IpoDto selectIpo(long ipoIndex) throws Exception;
    List<IpoScheduleDto> selectIpoScheduleList(Map<String, String> map) throws Exception;
    List<IpoCommentDto> selectIpoComment(long ipoIndex) throws Exception;
    List<IpoCommentDto> selectIpoCommentList(Map<String, Integer> map) throws Exception;
    List<IpoUnderwriterDto> selectIpoUnderwriter(long ipoIndex) throws Exception;
}
