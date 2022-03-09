package com.dbsgapi.dbsgapi.ipo.service;

import com.dbsgapi.dbsgapi.ipo.dto.IpoCommentDto;
import com.dbsgapi.dbsgapi.ipo.dto.IpoDto;
import com.dbsgapi.dbsgapi.ipo.dto.IpoSummaryDto;
import com.dbsgapi.dbsgapi.ipo.dto.IpoUnderwriterDto;

import java.util.List;

public interface IpoService {
    List<IpoSummaryDto> selectIpos() throws Exception;
    IpoDto selectIpo(long ipoIndex) throws Exception;
    List<IpoCommentDto> selectIpoComment(long ipoIndex) throws Exception;
    List<IpoCommentDto> selectIpoCommentList() throws Exception;
    List<IpoUnderwriterDto> selectIpoUnderwriter(long ipoIndex) throws Exception;
}
