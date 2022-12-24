package com.dbsgapi.dbsgapi.api.ipo.service;

import com.dbsgapi.dbsgapi.api.ipo.domain.DatePeriod;
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

    List<IpoSummaryDto> selectIpoScheduleList(DatePeriod datePeriod) throws IllegalStateException;

    List<IpoUnderwriterDto> selectIpoUnderwriter(long ipoIndex) throws IllegalStateException;
}
