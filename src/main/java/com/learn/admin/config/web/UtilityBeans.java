package com.learn.admin.config.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UtilityBeans {
    @Bean
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }
}
