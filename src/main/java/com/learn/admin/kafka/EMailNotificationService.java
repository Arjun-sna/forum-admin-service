package com.learn.admin.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EMailNotificationService<V> implements KafkaProducerService<V> {
    @Value("${spring.kafka.topics.email_notification}")
    private String topic;

    private final KafkaTemplate<Integer, V> kafkaTemplate;

    @Override
    public void send(V message) {
        kafkaTemplate.send(topic, message);
    }
}
