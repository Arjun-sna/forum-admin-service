package com.learn.admin.kafka;

import com.learn.admin.kafka.dto.PwResetNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PwResetNotificationService implements KafkaProducerService<PwResetNotification> {
    @Value("${spring.kafka.topics.pw_reset_notification}")
    private String topic;

    private final KafkaTemplate<Integer, PwResetNotification> kafkaTemplate;

    @Override
    public void send(PwResetNotification message) {
        kafkaTemplate.send(topic, message);
    }
}
