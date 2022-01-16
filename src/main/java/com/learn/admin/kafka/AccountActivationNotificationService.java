package com.learn.admin.kafka;

import com.learn.admin.kafka.dto.AccountActivationNotification;
import com.learn.admin.kafka.dto.PwResetNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountActivationNotificationService implements KafkaProducerService<AccountActivationNotification> {
    @Value("${spring.kafka.topics.account_activation_notification}")
    private String topic;

    private final KafkaTemplate<Integer, AccountActivationNotification> kafkaTemplate;

    @Override
    public void send(AccountActivationNotification message) {
        kafkaTemplate.send(topic, message);
    }
}
