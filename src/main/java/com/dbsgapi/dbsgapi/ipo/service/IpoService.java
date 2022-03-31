package com.dbsgapi.dbsgapi.ipo.service;

import com.dbsgapi.dbsgapi.ipo.dto.*;

import java.util.List;

public interface IpoService {
    List<IpoSummaryDto> selectIpos() throws Exception;
    IpoDto selectIpo(long ipoIndex) throws Exception;
    List<IpoScheduleDto> selectIpoScheduleList(String startDate, String endDate) throws Exception;
    List<IpoCommentDto> selectIpoComment(long ipoIndex) throws Exception;
    List<IpoCommentDto> selectIpoCommentList(int page, int num) throws Exception;
    List<IpoUnderwriterDto> selectIpoUnderwriter(long ipoIndex) throws Exception;
}
