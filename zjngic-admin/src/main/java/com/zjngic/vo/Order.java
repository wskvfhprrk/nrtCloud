package com.zjngic.vo;

import lombok.Data;

/**
 * Order 实体类，表示一个订单
 */
@Data
public class Order {

    /**
     * 订单ID
     */
    private String orderId;
    /**
     * 客户姓名
     */
    private String customerName;
    /**
     * 选择的食谱
     */
    private String selectedRecipe;
    /**
     * 选择的价格
     */
    private int selectedPrice;
    /**
     * 选择的调料
     */
    private String selectedSpice;
    /**
     * 是否加香菜
     */
    private boolean addCilantro;
    /**
     * 是否加洋葱
     */
    private boolean addOnion;
    /**
     * 订单的处理状态
     */
    private OrderStatus status;
    /**
     * 支付方式
     */
    private String paymentMethod;

}

