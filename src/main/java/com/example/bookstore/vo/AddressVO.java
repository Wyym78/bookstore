package com.example.bookstore.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AddressVO {

    private Long id;

    private Long userId;

    private String receiverName;

    private String phone;

    private String province;

    private String city;

    private String district;

    private String detailAddress;

    private Integer isDefault;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}