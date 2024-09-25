package com.zjngic.web.mqtt;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * https://blog.csdn.net/weixin_42575806/article/details/131513847
 */
@RestController
public class ConsumerController {
    @Autowired
    private MqttConsumerConfig client;

    @Value("${spring.mqtt.client.listen_id}")
    private String clientId;

    @RequestMapping("/connect")
    @ResponseBody
    public String connect(){
        client.connect();
        return clientId + "连接到服务器";
    }

    @RequestMapping("/disConnect")
    @ResponseBody
    public String disConnect(){
        client.disConnect();
        return clientId + "与服务器断开连接";
    }
}