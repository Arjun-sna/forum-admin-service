package com.learn.admin.kafka;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.kafka.topics")
public class KafkaTopicConfig {
    private String pwResetNotification;
    private String accountActivationNotification;
}
