package com.jay.fs.config;

import com.jay.fs.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 拦截器配置，装载登录验证拦截器
 * @author Jay
 */
@Configuration
public class AuthInterceptorConfig extends WebMvcConfigurationSupport {

    @Bean
    public AuthInterceptor initAuthInterceptor(){
        return new AuthInterceptor();
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(initAuthInterceptor()).addPathPatterns("/**/**").excludePathPatterns("/login");
    }
}
