package com.learn.admin.kafka.dto;

import com.learn.admin.kafka.KafkaTopicConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(staticName = "of")
public class AccountActivationNotification implements TopicAware {
    private final String token;
    private final int userId;
    private final String username;
    private final String email;

    @Override
    public String getTopic(KafkaTopicConfig kafkaTopicConfig) {
        return kafkaTopicConfig.getAccountActivationNotification();
    }
}
