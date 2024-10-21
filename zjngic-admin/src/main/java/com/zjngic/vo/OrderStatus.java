package com.zjngic.vo;

/**
 * 订单处理状态的枚举类
 */
public enum OrderStatus {
    TO_BE_PAID("待支付"),
    PAID("已支付"),
    REFUNDED("已退款"),
    PENDING("待加工"),
    PROCESSING("正在加工"),
    COMPLETED("已完成");

    private final String description; // 中文描述

    // 构造函数
    OrderStatus(String description) {
        this.description = description;
    }

    // 获取状态的中文描述
    public String getDescription() {
        return description;
    }
}
