package com.jc.nrtcloud.config;

import com.jc.nrtcloud.util.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.core.Ordered;

@Configuration
public class WebConfig {

    @Bean
    public FilterRegistrationBean<JwtAuthenticationFilter> jwtAuthenticationFilter() {
        FilterRegistrationBean<JwtAuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
        
        registrationBean.setFilter(new JwtAuthenticationFilter());
        registrationBean.addUrlPatterns("/api/*"); // 配置过滤路径, 例如保护所有 /api/ 下的请求
        registrationBean.setOrder(Ordered.LOWEST_PRECEDENCE);  // 设置过滤器的顺序

        return registrationBean;
    }
}
