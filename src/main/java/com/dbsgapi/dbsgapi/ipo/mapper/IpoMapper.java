package com.dbsgapi.dbsgapi.ipo.mapper;

import com.dbsgapi.dbsgapi.ipo.dto.IpoCommentDto;
import com.dbsgapi.dbsgapi.ipo.dto.IpoDto;
import com.dbsgapi.dbsgapi.ipo.dto.IpoSummaryDto;
import com.dbsgapi.dbsgapi.ipo.dto.IpoUnderwriterDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IpoMapper {
    List<IpoSummaryDto> selectIpos() throws Exception;
    IpoDto selectIpo(long ipoIndex) throws Exception;
    List<IpoCommentDto> selectIpoComment(long ipoIndex) throws Exception;
    List<IpoCommentDto> selectIpoCommentList() throws Exception;
    IpoUnderwriterDto selectIpoUnderwriter(long ipoIndex) throws Exception;
}
