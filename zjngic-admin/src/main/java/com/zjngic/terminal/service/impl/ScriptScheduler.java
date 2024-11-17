package com.zjngic.terminal.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Component
@Slf4j
public class ScriptScheduler {

    //    @Scheduled(cron = "0/1 * * * * ? ")  // 每天午夜12点执行
    @Scheduled(cron = "0 0 0 * * ?")  // 每天午夜12点执行
    public void runScript() {
        log.info("执行更新mysql密码脚本");
        try {
            // 执行脚本的命令
            ProcessBuilder processBuilder = new ProcessBuilder("/bin/bash", "/root/update_password.sh");
            processBuilder.redirectErrorStream(true);
            processBuilder.start();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }
}
