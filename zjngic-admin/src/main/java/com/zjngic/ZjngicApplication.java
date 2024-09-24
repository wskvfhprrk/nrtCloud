package com.zjngic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 * 
 * @author zjngic
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class ZjngicApplication{
    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(ZjngicApplication.class, args);
    }
}
