package com.zjngic.mqtt;

import com.alibaba.fastjson.JSON;
import com.hejz.pay.wx.WxNativePayTemplate;
import com.hejz.pay.wx.service.PaySuccessService;
import com.hejz.pay.wx.service.RefundSuccessService;
import com.hejz.util.service.SignService;
import com.zjngic.common.constant.Constants;
import com.zjngic.terminal.domain.OrderPayment;
import com.zjngic.terminal.domain.OriginalOrder;
import com.zjngic.terminal.domain.TerminalMachine;
import com.zjngic.terminal.service.IOriginalOrderService;
import com.zjngic.terminal.service.impl.OrderPaymentServiceImpl;
import com.zjngic.vo.OrderPayMessage;
import com.zjngic.vo.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 退款成功——服务端代码
 */
@Service
@Slf4j
public class PaySucessServiceImpl implements PaySuccessService, RefundSuccessService {
    @Autowired
    private MqttProviderConfig mqttProviderConfig;
    @Autowired
    private WxNativePayTemplate wxNativePayTemplate;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SignService signService;
    @Autowired
    private IOriginalOrderService originalOrderService;
    @Autowired
    private OrderPaymentServiceImpl orderPaymentService;


    @Override
    public void paySuccess(String outTradeNo) {
        // TODO: 2024/10/10 支付完成后就退款
        String refunds = wxNativePayTemplate.refunds(1, 1, outTradeNo);
        log.info("退款信息：{}", refunds);
        //支付完成后发送状态
        //需要使用订单号更换自定义订单号
        Object o = redisTemplate.opsForValue().get(Constants.PAY_ORDER_ID + "::" + outTradeNo);
        if (o == null) {
            log.error("已经没有此订单了");
            return;
        }
        OrderPayMessage orderPayMessage = JSON.parseObject(o.toString(), OrderPayMessage.class);
        orderPayMessage.setIsPaymentCompleted(true);
        redisTemplate.delete(Constants.PAY_ORDER_ID + "::" + outTradeNo);
        Object o1 = redisTemplate.opsForValue().get(Constants.REDIS_MACHINE_KEY + "::" + orderPayMessage.getMachineCode());
        if (o1 == null) {
            log.error("没有密钥");
            return;
        }
        try {
            // TODO: 2024/10/12 订单状态更改为已支付
            o = redisTemplate.opsForValue().get(Constants.ORIGINAL_ORDER_ID + "::" + outTradeNo);
            if (o == null) {
                log.error("缓存中没有订单：{}", outTradeNo);
                return;
            }
            TerminalMachine terminalMachine=(TerminalMachine) o1;
            String s = JSON.toJSONString(signService.signByData(String.valueOf(o), terminalMachine.getGeneratedKey()));
            mqttProviderConfig.publish(0, false, "message/paySuccess/" + terminalMachine.getCode(), s);
            //更新原始订单状态
            OriginalOrder order=new OriginalOrder();
            order.setOutTradeNo(outTradeNo);
            List<OriginalOrder> originalOrders = originalOrderService.selectOriginalOrderList(order);
            if(!originalOrders.isEmpty()){
                for (OriginalOrder originalOrder : originalOrders) {
                    originalOrder.setOrderStatus(OrderStatus.PAID.ordinal());
                    originalOrderService.updateOriginalOrder(originalOrder);
                }
            }
            //更新支付计单状态
            OrderPayment orderPayment=new OrderPayment();
            orderPayment.setOutTradeNo(outTradeNo);
            List<OrderPayment> orderPayments = orderPaymentService.selectOrderPaymentList(orderPayment);
            if(!orderPayments.isEmpty()){
                for (OrderPayment payment : orderPayments) {
                    payment.setPaymentStatus(OrderStatus.PAID.ordinal());
                    payment.setPayTime(new Date());
                    orderPaymentService.updateOrderPayment(payment);
                }
            }
            redisTemplate.delete(Constants.ORIGINAL_ORDER_ID + "::" + outTradeNo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void refundSuccess(String outTradeNo) {
        log.warn("退款成功，退款订单号：{}", outTradeNo);
        //更新原始订单状态
        OriginalOrder order=new OriginalOrder();
        order.setOutTradeNo(outTradeNo);
        List<OriginalOrder> originalOrders = originalOrderService.selectOriginalOrderList(order);
        if(!originalOrders.isEmpty()){
            for (OriginalOrder originalOrder : originalOrders) {
                originalOrder.setOrderStatus(OrderStatus.REFUNDED.ordinal());
                originalOrderService.updateOriginalOrder(originalOrder);
            }
        }
        //更新支付计单状态
        OrderPayment orderPayment=new OrderPayment();
        orderPayment.setOutTradeNo(outTradeNo);
        List<OrderPayment> orderPayments = orderPaymentService.selectOrderPaymentList(orderPayment);
        if(!orderPayments.isEmpty()){
            for (OrderPayment payment : orderPayments) {
                payment.setPaymentStatus(OrderStatus.REFUNDED.ordinal());
                payment.setRefundCode(outTradeNo);
                payment.setRefundTime(new Date());
//                payment.setRefundMethod();
                orderPaymentService.updateOrderPayment(payment);
            }
        }
    }
}
