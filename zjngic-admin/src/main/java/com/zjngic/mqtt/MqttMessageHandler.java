package com.zjngic.mqtt;

import com.alibaba.fastjson.JSON;
import com.hejz.pay.wx.WxNativePayTemplate;
import com.hejz.util.SignUtil;
import com.hejz.util.dto.SignDto;
import com.hejz.util.service.SignService;
import com.zjngic.common.constant.Constants;
import com.zjngic.common.core.domain.AjaxResult;
import com.zjngic.terminal.domain.TerminalMachine;
import com.zjngic.vo.OrderPayMessage;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
@Slf4j
public class MqttMessageHandler {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SignService signService;
    @Autowired
    private WxNativePayTemplate wxNativePayTemplate;
    @Autowired
    private MqttProviderConfig mqttProviderConfig;

    public void message(String topic, MqttMessage message) throws Exception {
        //查询缓存中的密钥
        Object o = redisTemplate.opsForValue().get(Constants.REDIS_MACHINE_KEY + "::" + topic.split("/")[2]);
        if (o == null) {
            log.error("缓存中没有密钥，topic：{}", topic);
            return;
        }
        TerminalMachine terminalMachine = (TerminalMachine) o;
        if (terminalMachine.getGeneratedKey() == null || terminalMachine.getGeneratedKey().length() == 0) {
            log.error("没有密钥，topic：{}", topic);
            return;
        }
        Boolean flag = signService.verifyData(String.valueOf(message), terminalMachine.getGeneratedKey());
        if (!flag) {
            log.error("未通过签名验证");
            mqttProviderConfig.publish(0, false, "/message/error/" + topic.split("/")[1], JSON.toJSONString(signService.signByData("签名未通过验证", terminalMachine.getGeneratedKey())));
            return;
        }

        //根据订单获取二维码
        if (topic.split("/")[1].equals("order")) {
            //todo 制作订单号
            Long increment = redisTemplate.opsForValue().increment(Constants.ORDER_ID + "::" + topic.split("/")[2]) + 1000;
            //不能超过4位数字，到头从头开始
            if(increment>=8998){
                redisTemplate.opsForValue().set(Constants.ORDER_ID + "::" + topic.split("/")[2],0);
            }
            // 将序列号转换为字符串并取前4位
            // 若不足4位，可以根据业务需要，决定是否进行补位（例如补0）
            String id = String.format("%04d", increment);
            String orderId = "A" + id;
            //交易订单id
            String outTradeNo = UUID.randomUUID().toString().replace("-", "");
            // TODO: 2024/10/10 钱根据实际支付测试时都为一分钱
            String s = wxNativePayTemplate.createOrder(1, outTradeNo, "取餐号：" + orderId);
            log.info("支付完成返回的二维码信息：{}", s);
            // TODO: 2024/10/12 修改订单状态为待支付状态
            if (s.split(",")[0].equals("200")) {
                Map map1 = JSON.parseObject(s.split(",")[1], Map.class);
                String codeUrl = map1.get("code_url").toString();
                //二维码发进mqtt中
                OrderPayMessage orderPayMessage = new OrderPayMessage();
                orderPayMessage.setPayMethod("wechat");
                orderPayMessage.setOrderId(orderId);
                orderPayMessage.setIsPaymentCompleted(false);
                orderPayMessage.setQrCodeText(codeUrl);
                orderPayMessage.setOutTradeNo(outTradeNo);
                orderPayMessage.setMachineCode(topic.split("/")[2]);
                //发送mqtt消息给客户端
                SignDto dto1=new SignDto();
                dto1.setData(JSON.toJSONString(orderPayMessage));
                dto1.setTimestamp(System.currentTimeMillis());
                dto1.setNonce(SignUtil.generateNonce(16));
                mqttProviderConfig.publish(0, false, "message/pay/" + topic.split("/")[2], JSON.toJSONString(signService.signDataToVo(dto1,terminalMachine.getGeneratedKey())));
                //缓存下订单，等成功能后根据订单id再删除
                redisTemplate.opsForValue().set(Constants.PAY_ORDER_ID + "::" + orderPayMessage.getOutTradeNo(), JSON.toJSONString(orderPayMessage));
            }
        }
    }
}
