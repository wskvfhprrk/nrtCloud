package com.jc.nrtcloud.sevice;

import com.jc.nrtcloud.util.JwtAuthenticationFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public WebConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor((HandlerInterceptor) jwtAuthenticationFilter)
                .addPathPatterns("/**")  // 所有请求都需要验证 Token
                .excludePathPatterns("/auth/**");  // 登录接口除外
    }
}
