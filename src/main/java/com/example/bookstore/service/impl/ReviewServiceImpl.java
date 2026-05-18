package com.example.bookstore.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.bookstore.common.Constants;
import com.example.bookstore.entity.Book;
import com.example.bookstore.entity.Review;
import com.example.bookstore.entity.User;
import com.example.bookstore.exception.BusinessException;
import com.example.bookstore.mapper.BookMapper;
import com.example.bookstore.mapper.ReviewMapper;
import com.example.bookstore.mapper.UserMapper;
import com.example.bookstore.service.ReviewService;
import com.example.bookstore.vo.ReviewVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewMapper reviewMapper;
    private final BookMapper bookMapper;
    private final UserMapper userMapper;

    @Override
    public void add(Long userId, Review review) {
        Book book = bookMapper.selectById(review.getBookId());
        if (book == null) {
            throw new BusinessException(1, "书籍不存在");
        }

        review.setUserId(userId);
        review.setStatus(Constants.REVIEW_SHOW);
        reviewMapper.insert(review);
    }

    @Override
    public List<ReviewVO> getByBookId(Long bookId) {
        LambdaQueryWrapper<Review> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Review::getBookId, bookId)
                .eq(Review::getStatus, Constants.REVIEW_SHOW)
                .orderByDesc(Review::getCreateTime);
        List<Review> reviews = reviewMapper.selectList(wrapper);

        return reviews.stream().map(review -> {
            ReviewVO vo = new ReviewVO();
            vo.setId(review.getId());
            vo.setUserId(review.getUserId());
            vo.setBookId(review.getBookId());
            vo.setRating(review.getRating());
            vo.setContent(review.getContent());
            vo.setCreateTime(review.getCreateTime());

            User user = userMapper.selectById(review.getUserId());
            if (user != null) {
                vo.setUsername(user.getUsername());
            }
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public void delete(Long userId, Long reviewId) {
        Review review = reviewMapper.selectById(reviewId);
        if (review == null) {
            throw new BusinessException(1, "评论不存在");
        }
        if (!review.getUserId().equals(userId)) {
            throw new BusinessException(1, "无权删除此评论");
        }
        reviewMapper.deleteById(reviewId);
    }
}