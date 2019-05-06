package com.jk.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class LoginConfiguration extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LoginInterceptor loginInterceptor = new LoginInterceptor();
        // addPathPatterns("/**") 表示拦截所有的请求，
        // excludePathPatterns("/login", "/register") 表示除了登陆与注册之外，因为登陆注册不需要登陆也可以访问
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns("/page/toLogin","/page/login2","/login","/gainMessgerCode","/messageLogin");
        //registry.addInterceptor(powerInterceptor).addPathPatterns("/**").excludePathPatterns("/page/login", "/book/login");
    }
}
