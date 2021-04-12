package com.imooc.reader.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imooc.reader.entity.Book;

/**
 * 图书mapper接口
 */
public interface BookMapper extends BaseMapper<Book> {
    /**
     * 更新图书评分/评价数量
     */
    public void updateEvalueation();
}
