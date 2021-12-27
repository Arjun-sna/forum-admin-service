package com.learn.admin.config.web;

import com.learn.admin.util.UserOrderConverter;
import com.learn.admin.util.UserSortConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new UserSortConverter());
        registry.addConverter(new UserOrderConverter());
    }
}
