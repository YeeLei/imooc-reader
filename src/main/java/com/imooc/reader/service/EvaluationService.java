package com.imooc.reader.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.imooc.reader.entity.Book;
import com.imooc.reader.entity.Evaluation;

import java.util.List;

public interface EvaluationService {
    /**
     * 按图书编号查询有效短评
     * @param bookId 图书编号
     * @return 评论列表
     */
    public List<Evaluation> selectByBookId(Long bookId);

    /**
     * 分页查询短评
     * @param page 页号
     * @param rows 每页显示条数
     * @return 分页对象
     */
    public IPage<Evaluation> paging(Integer page, Integer rows);

    /**
     * 禁用短评
     * @param evaluationId 短评id
     * @param reason 禁用原因
     */
    public void disableEvaluation(Long evaluationId,String reason);
}
