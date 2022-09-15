package com.dbsgapi.dbsgapi.api.ipo.service;

import com.dbsgapi.dbsgapi.api.ipo.dto.IpoCommentDto;
import com.dbsgapi.dbsgapi.api.ipo.dto.IpoDto;
import com.dbsgapi.dbsgapi.api.ipo.dto.IpoSummaryDto;
import com.dbsgapi.dbsgapi.api.ipo.dto.IpoUnderwriterDto;
import com.dbsgapi.dbsgapi.api.ipo.dto.*;

import java.time.LocalDate;
import java.util.List;

public interface IpoService {
    List<IpoSummaryDto> selectIpos(String queryString, int page, int num) throws Exception;
    IpoDto selectIpo(long ipoIndex) throws Exception;
    List<IpoSummaryDto> selectIpoScheduleList(String startDate, String endDate) throws Exception;
    List<IpoCommentDto> selectIpoComment(long ipoIndex) throws Exception;
    IpoCommentDto selectIpoCommentIndex(long commentIndex) throws Exception;
    List<IpoCommentDto> selectIpoCommentList(LocalDate startDate, LocalDate endDate) throws Exception;
    List<IpoUnderwriterDto> selectIpoUnderwriter(long ipoIndex) throws Exception;
}
