package com.example.bookstore.common;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class PageResult<T> implements Serializable {
    private Long total;
    private T records;

    public PageResult(Long total, T records) {
        this.total = total;
        this.records = records;
    }
}