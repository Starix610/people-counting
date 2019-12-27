package com.weiyun.peoplecounting.config;

import com.weiyun.peoplecounting.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration
public class LoginConfiguration extends WebMvcConfigurerAdapter {

    @Bean
    public LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(loginInterceptor())
        .addPathPatterns("/**")
        .excludePathPatterns("/user/login")
        .excludePathPatterns("/user/error/notlogined")
        .excludePathPatterns("/user/sendcode")
        .excludePathPatterns("/upload/base64")
        .excludePathPatterns("/upload")
        .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**") //swagger的路径
        .excludePathPatterns("/user/register");

    }
}
