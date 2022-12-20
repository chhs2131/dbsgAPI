package com.dbsgapi.dbsgapi.api.ipo.service;

import com.dbsgapi.dbsgapi.api.ipo.domain.IpoPaging;
import com.dbsgapi.dbsgapi.api.ipo.domain.IpoSequence;
import com.dbsgapi.dbsgapi.api.ipo.dto.IpoCommentDto;
import com.dbsgapi.dbsgapi.api.ipo.dto.IpoDto;
import com.dbsgapi.dbsgapi.api.ipo.dto.IpoSummaryDto;
import com.dbsgapi.dbsgapi.api.ipo.dto.IpoUnderwriterDto;

import java.time.LocalDate;
import java.util.List;

public interface IpoService {
    List<IpoSummaryDto> selectIpos(IpoPaging ipoPaging) throws IllegalStateException;

    IpoDto selectIpo(long ipoIndex) throws IllegalStateException;

    List<IpoSummaryDto> selectIpoScheduleList(String startDate, String endDate) throws IllegalStateException;

    List<IpoCommentDto> selectIpoComment(long ipoIndex) throws IllegalStateException;

    IpoCommentDto selectIpoCommentIndex(long commentIndex) throws IllegalStateException;

    List<IpoCommentDto> selectIpoCommentList(LocalDate startDate, LocalDate endDate) throws IllegalStateException;

    List<IpoUnderwriterDto> selectIpoUnderwriter(long ipoIndex) throws IllegalStateException;
}
