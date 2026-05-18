package com.example.bookstore.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.bookstore.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("book")
public class Book extends BaseEntity {

    private String isbn;

    private String title;

    private String author;

    private String publisher;

    private LocalDate publishDate;

    private BigDecimal price;

    private Integer stock;

    private Long categoryId;

    private String description;

    private String coverUrl;

    private Integer status;
}