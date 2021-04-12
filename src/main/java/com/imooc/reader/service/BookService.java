package com.imooc.reader.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.imooc.reader.entity.Book;

/**
 * 图书服务
 */
public interface BookService {
    /**
     * 分页查询图书
     * @param categoryId 分类编号
     * @param order 排序方式
     * @param page 页号
     * @param rows 每页显示条数
     * @return 分页对象
     */
    public IPage<Book> paging(Long categoryId, String order, Integer page, Integer rows);

    /**
     * 根据图书编号查询图书对象
     * @param bookId 图书编号
     * @return 图书对象
     */
    public Book selectById(Long bookId);

    /**
     * 更新图书评分/评价数量
     */
    public void updateEvalueation();

    /**
     * 新增图书信息
     * @param book 图书信息
     * @return 图书信息
     */
    public Book createBook(Book book);

    /**
     * 更新图书信息
     * @param book 新图书信息
     * @return 新图书信息
     */
    public Book updateBook(Book book);

    /**
     * 根据bookId删除图书
     * @param bookId 图书Id
     */
    public void deleteBook(Long bookId);
}
