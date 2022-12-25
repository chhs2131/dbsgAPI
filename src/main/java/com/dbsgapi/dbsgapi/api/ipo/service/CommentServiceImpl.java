package com.dbsgapi.dbsgapi.api.ipo.service;

import com.dbsgapi.dbsgapi.api.ipo.domain.DatePeriod;
import com.dbsgapi.dbsgapi.api.ipo.dto.IpoCommentDto;
import com.dbsgapi.dbsgapi.api.ipo.mapper.CommentMapper;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private static final String NEW_REGISTER_COMMENT = "신규 등록되었습니다.";

    private final CommentMapper commentMapper;

    @Override
    public List<IpoCommentDto> selectIpoComment(long ipoIndex) throws IllegalStateException {
        List<IpoCommentDto> ipoCommentList = commentMapper.selectIpoComment(ipoIndex);
        removeEmptyComment(ipoCommentList);

        return ifPresent(ipoCommentList);
    }

    @Override
    public IpoCommentDto selectIpoCommentIndex(long commentIndex) throws IllegalStateException {
        return commentMapper.selectIpoCommentIndex(commentIndex).orElseThrow(IllegalStateException::new);
    }

    @Override
    public List<IpoCommentDto> selectIpoCommentList(DatePeriod datePeriod) throws IllegalStateException {
        // 쿼리문 요청, 조회
        Map<String, Object> map = datePeriod.toMap();
        List<IpoCommentDto> ipoCommentList = commentMapper.selectIpoCommentList(map);
        removeEmptyComment(ipoCommentList);

        List<IpoCommentDto> newRegisterComments = ipoCommentList.stream()
                .filter(comment -> NEW_REGISTER_COMMENT.equals(comment.getTitle()))
                .collect(Collectors.toList());
        System.out.println(newRegisterComments.toString());

        ipoCommentList = ipoCommentList.stream()
                .filter(comment -> !isCommentInComments(comment, newRegisterComments))
                .collect(Collectors.toList());

        //TODO 고민점: 신규상장일 경우, 해당일에 다른 코멘트들에 내용을 신규상장쪽으로 이전하고, 이전당한 코멘트는 제거?
        return ifPresent(ipoCommentList);
    }

    private static boolean isCommentInComments(IpoCommentDto comment, List<IpoCommentDto> newRegisterComments) {
        return newRegisterComments.stream()
                .anyMatch(c -> isCommentsSameIndexAndRegisterDate(c, comment));
    }

    private static boolean isCommentsSameIndexAndRegisterDate(IpoCommentDto comment1, IpoCommentDto comment2) {
        if (comment1 == null || comment2 == null) {
            return false;
        }
        if (comment1.getCommentIndex() == comment2.getCommentIndex()) { // 신규상장
            return false;
        }
        if (comment1.getIpoIndex() != comment2.getIpoIndex()) {  // 동일종목
            return false;
        }
        if (!comment1.getRegistDate().equals(comment2.getRegistDate())) {  // 같은 등록일
            return false;
        }
        return true;
    }

    private static void removeEmptyComment(List<IpoCommentDto> ipoCommentList) {
        // 결과 중 내용이 없는 코멘트가 있는 경우 제거한다.
        ipoCommentList.removeIf(ipoComment -> "".equals(ipoComment.getTitle()));
    }

    private <T> List<T> ifPresent(List<T> targetList) {
        validateListNull(targetList);
        validateListEmpty(targetList);
        return targetList;
    }

    private <T> void validateListNull(List<T> targetList) {
        if (targetList == null) {
            throw new IllegalStateException();
        }
    }

    private <T> void validateListEmpty(List<T> targetList) {
        if (targetList.isEmpty()) {
            throw new IllegalStateException();
        }
    }
}
