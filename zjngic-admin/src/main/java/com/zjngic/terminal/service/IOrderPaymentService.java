package com.zjngic.terminal.service;

import java.util.List;
import com.zjngic.terminal.domain.OrderPayment;

/**
 * 订单支付Service接口
 * 
 * @author zjngic
 * @date 2024-10-22
 */
public interface IOrderPaymentService 
{
    /**
     * 查询订单支付
     * 
     * @param id 订单支付主键
     * @return 订单支付
     */
    public OrderPayment selectOrderPaymentById(Long id);

    /**
     * 查询订单支付列表
     * 
     * @param orderPayment 订单支付
     * @return 订单支付集合
     */
    public List<OrderPayment> selectOrderPaymentList(OrderPayment orderPayment);

    /**
     * 新增订单支付
     * 
     * @param orderPayment 订单支付
     * @return 结果
     */
    public int insertOrderPayment(OrderPayment orderPayment);

    /**
     * 修改订单支付
     * 
     * @param orderPayment 订单支付
     * @return 结果
     */
    public int updateOrderPayment(OrderPayment orderPayment);

    /**
     * 批量删除订单支付
     * 
     * @param ids 需要删除的订单支付主键集合
     * @return 结果
     */
    public int deleteOrderPaymentByIds(Long[] ids);

    /**
     * 删除订单支付信息
     * 
     * @param id 订单支付主键
     * @return 结果
     */
    public int deleteOrderPaymentById(Long id);
}
