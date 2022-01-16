package com.learn.admin.kafka.dto;

import com.learn.admin.kafka.KafkaTopicConfig;
import lombok.*;

@Data
@RequiredArgsConstructor(staticName = "of")
public class PwResetNotification implements TopicAware {
    private final String token;
    private final int userId;
    private final String username;
    private final String email;

    @Override
    public String getTopic(KafkaTopicConfig kafkaTopicConfig) {
        return kafkaTopicConfig.getPwResetNotificationTopic();
    }
}
