package com.learn.admin.kafka;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class KafkaTopicConfig {
    @Value("${spring.kafka.topics.pw_reset_notification}")
    private String pwResetNotificationTopic;

    @Value("${spring.kafka.topics.account_activation_notification}")
    private String accountActivationNotificationTopic;
}
