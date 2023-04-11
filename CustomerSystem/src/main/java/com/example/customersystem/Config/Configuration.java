package com.example.customersystem.Config;

import com.example.customersystem.Filter.InputFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import jakarta.servlet.Filter;
import java.util.ArrayList;
import java.util.List;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public FilterRegistrationBean<Filter> filterRegistrationBean(){
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        List<String> patterns = new ArrayList<>();
        patterns.add("/login");
        filterRegistrationBean.setUrlPatterns(patterns);
        filterRegistrationBean.setFilter(new InputFilter());
        return filterRegistrationBean;
    }

}

