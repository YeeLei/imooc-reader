package com.imooc.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.reader.entity.Book;
import com.imooc.reader.entity.Evaluation;
import com.imooc.reader.entity.Member;
import com.imooc.reader.mapper.BookMapper;
import com.imooc.reader.mapper.EvaluationMapper;
import com.imooc.reader.mapper.MemberMapper;
import com.imooc.reader.service.EvaluationService;
import com.imooc.reader.service.exception.BussinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service("evaluationService")
@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
public class EvaluationServiceImpl implements EvaluationService {
    @Resource
    private EvaluationMapper evaluationMapper;
    @Resource
    private MemberMapper memberMapper;
    @Resource
    private BookMapper bookMapper;

    /**
     * 按图书编号查询有效短评
     *
     * @param bookId 图书编号
     * @return 评论列表
     */
    public List<Evaluation> selectByBookId(Long bookId) {
        Book book = bookMapper.selectById(bookId);
        QueryWrapper<Evaluation> queryWrapper = new QueryWrapper<Evaluation>();
        queryWrapper.eq("book_id", bookId);
        queryWrapper.eq("state", "enable");
        queryWrapper.orderByDesc("create_time");
        List<Evaluation> evaluationList = evaluationMapper.selectList(queryWrapper);
        for (Evaluation evaluation : evaluationList) {
            Long memberId = evaluation.getMemberId();
            Member member = memberMapper.selectById(memberId);
            evaluation.setMember(member);
            evaluation.setBook(book);
        }
        return evaluationList;
    }

    /**
     * 分页查询短评
     *
     * @param page 页号
     * @param rows 每页显示条数
     * @return 分页对象
     */
    public IPage<Evaluation> paging(Integer page, Integer rows) {
        Page<Evaluation> p = new Page<Evaluation>(page, rows);
        QueryWrapper<Evaluation> queryWrapper = new QueryWrapper<Evaluation>();
        Page<Evaluation> pageObject = evaluationMapper.selectPage(p, queryWrapper);
        List<Evaluation> evaluations = pageObject.getRecords();
        for (Evaluation evaluation : evaluations) {
            Member member = memberMapper.selectById(evaluation.getMemberId());
            evaluation.setMember(member);
        }
        for (Evaluation evaluation : evaluations) {
            Book book = bookMapper.selectById(evaluation.getBookId());
            evaluation.setBook(book);
        }
        return pageObject;
    }

    /**
     * 禁用短评
     *
     * @param evaluationId 短评id
     * @param reason       禁用原因
     */
    public void disableEvaluation(Long evaluationId, String reason) {
        Evaluation evaluation = evaluationMapper.selectById(evaluationId);
        if (evaluation ==null) {
            throw new BussinessException("M101","短评不能为空");
        }
        if (reason == null) {
            throw new BussinessException("M102","禁用原因不能为空");
        }
        evaluation.setState("disable");
        evaluation.setDisableReason(reason);
        evaluation.setDisableTime(new Date());
        evaluationMapper.updateById(evaluation);
    }
}
