package com.dbsgapi.dbsgapi.ipo.service;

import com.dbsgapi.dbsgapi.ipo.dto.*;

import java.util.List;

public interface IpoService {
    List<IpoSummaryDto> selectIpos(String kind, int page, int num) throws Exception;
    IpoDto selectIpo(long ipoIndex) throws Exception;
    List<IpoSummaryDto> selectIpoScheduleList(String startDate, String endDate) throws Exception;
    List<IpoCommentDto> selectIpoComment(long ipoIndex) throws Exception;
    IpoCommentDto selectIpoCommentIndex(long commentIndex) throws Exception;
    List<IpoCommentDto> selectIpoCommentList(int page, int num) throws Exception;
    List<IpoUnderwriterDto> selectIpoUnderwriter(long ipoIndex) throws Exception;
}
