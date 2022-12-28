package com.dbsgapi.dbsgapi.api.ipo.service;

import com.dbsgapi.dbsgapi.api.ipo.domain.DatePeriod;
import com.dbsgapi.dbsgapi.api.ipo.dto.IpoCommentDto;
import java.util.List;

public interface CommentService {
    List<IpoCommentDto> selectIpoComment(long ipoIndex) throws IllegalStateException;

    IpoCommentDto selectIpoCommentIndex(long commentIndex) throws IllegalStateException;

    List<IpoCommentDto> selectIpoCommentList(DatePeriod datePeriod) throws IllegalStateException;
}
