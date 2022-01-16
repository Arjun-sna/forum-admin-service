package com.learn.admin.kafka;

import com.learn.admin.kafka.dto.TopicAware;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationService<T extends TopicAware> implements KafkaNotificationService<T> {
    private final KafkaTemplate<Integer, T> kafkaTemplate;
    private final KafkaTopicConfig kafkaTopicConfig;

    @Override
    public void send(T message) {
        kafkaTemplate.send(message.getTopic(kafkaTopicConfig), message);
    }
}
