package com.example.keijiban.config;

import com.example.keijiban.filter.LoginFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<LoginFilter> loginFilter() {
        FilterRegistrationBean<LoginFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new LoginFilter());
        registrationBean.addUrlPatterns("/keijiban", "/new", "/management", "/userEdit/*", "/registration");
        registrationBean.setName("LoginFilter");
        registrationBean.setOrder(1); // 複数のFilterがある場合は優先度を設定

        return registrationBean;
    }
}