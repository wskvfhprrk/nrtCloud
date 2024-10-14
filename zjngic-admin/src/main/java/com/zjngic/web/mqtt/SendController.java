package com.zjngic.web.mqtt;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 创建控制器测试发布信息
 */
@Controller
public class SendController {
    @Autowired
    private MqttProviderConfig providerClient;

    @RequestMapping("/sendMessage")
    @ResponseBody
    public String sendMessage(int qos,boolean retained,String topic,String message){
        try {
            providerClient.publish(qos, retained, topic, message);
            return "发送成功";
        } catch (Exception e) {
            e.printStackTrace();
            return "发送失败";
        }
    }

//    @PostConstruct
    public void init(){
        while (true){
            try {
                Thread.sleep(30000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sendMessage(1,true,"topic5", "{\n" +
                    "  \"date\":" +LocalDateTime.now()+
                    "\n}");
        }
    }
}