package com.example.bookstore.common;

public interface Constants {

    // ========== 角色 ==========
    String ROLE_USER = "user";
    String ROLE_ADMIN = "admin";

    // ========== 用户状态 ==========
    Integer STATUS_NORMAL = 1;
    Integer STATUS_DISABLED = 0;

    // ========== 书籍状态 ==========
    Integer BOOK_STATUS_ON = 1;   // 上架
    Integer BOOK_STATUS_OFF = 0;  // 下架

    // ========== 地址默认状态 ==========
    Integer ADDRESS_DEFAULT = 1;    // 默认地址
    Integer ADDRESS_NOT_DEFAULT = 0;

    // ========== 评论状态 ==========
    Integer REVIEW_SHOW = 1;   // 显示
    Integer REVIEW_HIDE = 0;   // 隐藏

    // ========== 订单状态 ==========
    String ORDER_STATUS_PENDING = "pending";        // 待支付
    String ORDER_STATUS_PAID = "paid";             // 已支付
    String ORDER_STATUS_SHIPPED = "shipped";       // 已发货
    String ORDER_STATUS_DELIVERED = "delivered";   // 已收货
    String ORDER_STATUS_COMPLETED = "completed";   // 已完成
    String ORDER_STATUS_CANCELLED = "cancelled";   // 已取消

    // ========== 支付状态 ==========
    String PAY_STATUS_UNPAID = "unpaid";    // 未支付
    String PAY_STATUS_PAID = "paid";       // 已支付
    String PAY_STATUS_REFUNDED = "refunded"; // 已退款

    // ========== 订单状态流转 ==========
    // pending → paid → shipped → delivered → completed
    //    ↓
    // cancelled（从 pending 可取消）
    // shipped → delivered → completed（确认收货）
    // paid/shipped → refunded（退款）
}