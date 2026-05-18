package com.example.bookstore.service;

import com.example.bookstore.dto.BookQueryDTO;
import com.example.bookstore.vo.BookDetailVO;
import com.example.bookstore.vo.BookVO;
import com.example.bookstore.common.PageResult;

public interface BookService {

    PageResult<BookVO> pageQuery(BookQueryDTO queryDTO);

    PageResult<BookVO> findByCategory(Long categoryId, Integer pageNum, Integer pageSize);

    PageResult<BookVO> search(String keyword, Integer pageNum, Integer pageSize);

    BookDetailVO getDetail(Long id);
}