package com.zjngic.mqtt;

import com.alibaba.fastjson.JSON;
import com.hejz.pay.wx.WxNativePayTemplate;
import com.hejz.pay.wx.service.PaySuccessService;
import com.hejz.pay.wx.service.RefundSuccessService;
import com.hejz.util.service.SignService;
import com.zjngic.common.constant.Constants;
import com.zjngic.terminal.domain.TerminalMachine;
import com.zjngic.vo.OrderPayMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

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


    @Override
    public void paySuccess(String outTradeNo) {
        // TODO: 2024/10/10 支付完成后就退款
        String refunds = wxNativePayTemplate.refunds(1, 1, outTradeNo);
        log.info("退款信息：{}", refunds);
        //支付完成后发送状态
        //需要使用订单号更换自定义订单号
        Object o = redisTemplate.opsForValue().get(Constants.PAY_ORDER_ID + "::" + outTradeNo);
        if(o==null){
            log.error("已经没有此订单了");
            return;
        }
        OrderPayMessage orderPayMessage =JSON.parseObject(o.toString(),OrderPayMessage.class);
        orderPayMessage.setIsPaymentCompleted(true);
        redisTemplate.delete(Constants.PAY_ORDER_ID + "::" + outTradeNo);
        Object o1 = redisTemplate.opsForValue().get(Constants.REDIS_MACHINE_KEY+"::"+orderPayMessage.getMachineCode());
        if (o1 == null) {
            log.error("没有密钥");
            return;
        }
        TerminalMachine terminalMachine=(TerminalMachine) o1;
        try {
            String s = JSON.toJSONString(signService.signByData(JSON.toJSONString(orderPayMessage), terminalMachine.getPassword()));
            // TODO: 2024/10/12 订单状态更改为已支付
            mqttProviderConfig.publish(0, false, "message/paySuccess/" + terminalMachine.getCode(), s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void refundSuccess(String outTradeNo) {
        log.warn("退款成功，退款订单号：{}", outTradeNo);
    }
    //获取订单
}
