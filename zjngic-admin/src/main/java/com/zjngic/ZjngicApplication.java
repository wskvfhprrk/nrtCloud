package com.zjngic;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 启动程序
 *
 * @author zjngic
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan(basePackages = {"com.hejz","com.zjngic"})
@EnableScheduling
public class ZjngicApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZjngicApplication.class, args);
    }
}
