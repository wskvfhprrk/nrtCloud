package com.zjngic.mqtt;

import com.alibaba.fastjson2.JSON;
import com.zjngic.common.core.domain.AjaxResult;
import com.zjngic.terminal.domain.TerminalMachine;
import com.zjngic.terminal.mapper.TerminalMachineMapper;
import com.zjngic.terminal.service.ITerminalMachineService;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class MqttConsumerCallBack implements MqttCallback {

    @Autowired
    @Lazy
    private MqttConsumerConfig mqttConsumerConfig;

    @Autowired
    private ITerminalMachineService terminalMachineService;

    @Autowired
    private MqttProviderConfig mqttProviderConfig;
    @Autowired
    private MqttMessageHandler mqttMessageHandler;

    private static final int MAX_RETRIES = 3; // 最大重试次数
    private static final int RETRY_INTERVAL_MS = 3000; // 每次重试间隔，单位为毫秒
    private int retryCount = 0; // 当前重试次数

    /**
     * 客户端断开连接的回调
     */
    @Override
    public void connectionLost(Throwable throwable) {
        log.info("与服务器断开连接，正在新重连");
        reconnect();
    }

    private void reconnect() {
        while (retryCount < MAX_RETRIES) {
            try {
                mqttConsumerConfig.connect();
                log.info("重连成功");
                retryCount = 0; // 重置重试计数器
                break;
            } catch (Exception e) {
                retryCount++;
                log.error("重连失败，第 " + retryCount + " 次尝试");

                if (retryCount >= MAX_RETRIES) {
                    log.error("达到最大重连次数，停止重连");
                    break;
                }

                try {
                    Thread.sleep(RETRY_INTERVAL_MS); // 等待指定时间后再重试
                } catch (InterruptedException ie) {
                    log.error("重连等待期间被打断", ie);
                    Thread.currentThread().interrupt(); // 恢复线程的中断状态
                    break;
                }
            }
        }
    }

    /**
     * 消息到达的回调
     */
    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        log.info(String.format("接收消息主题 : %s", topic));
        log.info(String.format("接收消息Qos : %d", message.getQos()));
        log.info(String.format("接收消息内容 : %s", new String(message.getPayload())));
        log.info(String.format("接收消息retained : %b", message.isRetained()));
        //根据topic处理消息
        processMessagesBasedOnTopic(topic, message);

    }

    /**
     * 根据topic处理消息——topic作为路由
     *
     * @param topic
     * @param message
     */
    private void processMessagesBasedOnTopic(String topic, MqttMessage message) {
        //获取密钥，不用签名的
        if (topic.split("/")[0].equals("getKey")) {
            Map map = JSON.parseObject(String.valueOf(message), Map.class);
            TerminalMachine terminalMachine = new TerminalMachine();
            terminalMachine.setCode(map.get("code").toString());
            terminalMachine.setPassword(map.get("password").toString());
            List<TerminalMachine> terminalMachines = terminalMachineService.selectTerminalMachineList(terminalMachine);
            if (terminalMachines.isEmpty() ) {
                log.error("获取密钥值密码不正确：{}", message);
                mqttProviderConfig.publish(0, false, "key/" + topic.split("/")[1], JSON.toJSONString(AjaxResult.error("机器码和密码错误！")));
                return;
            }
            AjaxResult result = terminalMachineService.getCerts(terminalMachine);
            mqttProviderConfig.publish(0, false, "key/" + topic.split("/")[1], JSON.toJSONString(result));
        }else {
            try {
                mqttMessageHandler.message(topic,message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 消息发布成功的回调
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }

}