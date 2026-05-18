package com.example.bookstore.service;

import com.example.bookstore.entity.Review;
import com.example.bookstore.vo.ReviewVO;

import java.util.List;

public interface ReviewService {

    void add(Long userId, Review review);

    List<ReviewVO> getByBookId(Long bookId);

    void delete(Long userId, Long reviewId);
}