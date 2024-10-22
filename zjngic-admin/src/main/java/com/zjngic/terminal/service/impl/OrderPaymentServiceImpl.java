package com.zjngic.terminal.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zjngic.terminal.mapper.OrderPaymentMapper;
import com.zjngic.terminal.domain.OrderPayment;
import com.zjngic.terminal.service.IOrderPaymentService;

/**
 * 订单支付Service业务层处理
 * 
 * @author zjngic
 * @date 2024-10-22
 */
@Service
public class OrderPaymentServiceImpl implements IOrderPaymentService 
{
    @Autowired
    private OrderPaymentMapper orderPaymentMapper;

    /**
     * 查询订单支付
     * 
     * @param id 订单支付主键
     * @return 订单支付
     */
    @Override
    public OrderPayment selectOrderPaymentById(Long id)
    {
        return orderPaymentMapper.selectOrderPaymentById(id);
    }

    /**
     * 查询订单支付列表
     * 
     * @param orderPayment 订单支付
     * @return 订单支付
     */
    @Override
    public List<OrderPayment> selectOrderPaymentList(OrderPayment orderPayment)
    {
        return orderPaymentMapper.selectOrderPaymentList(orderPayment);
    }

    /**
     * 新增订单支付
     * 
     * @param orderPayment 订单支付
     * @return 结果
     */
    @Override
    public int insertOrderPayment(OrderPayment orderPayment)
    {
        return orderPaymentMapper.insertOrderPayment(orderPayment);
    }

    /**
     * 修改订单支付
     * 
     * @param orderPayment 订单支付
     * @return 结果
     */
    @Override
    public int updateOrderPayment(OrderPayment orderPayment)
    {
        return orderPaymentMapper.updateOrderPayment(orderPayment);
    }

    /**
     * 批量删除订单支付
     * 
     * @param ids 需要删除的订单支付主键
     * @return 结果
     */
    @Override
    public int deleteOrderPaymentByIds(Long[] ids)
    {
        return orderPaymentMapper.deleteOrderPaymentByIds(ids);
    }

    /**
     * 删除订单支付信息
     * 
     * @param id 订单支付主键
     * @return 结果
     */
    @Override
    public int deleteOrderPaymentById(Long id)
    {
        return orderPaymentMapper.deleteOrderPaymentById(id);
    }
}
