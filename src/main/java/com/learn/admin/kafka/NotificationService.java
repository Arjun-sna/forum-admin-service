package com.learn.admin.kafka;

import com.learn.admin.kafka.dto.PwResetNotification;
import com.learn.admin.kafka.dto.TopicAware;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TestImplementation<T extends TopicAware> implements KafkaProducerService<T>{
    private final KafkaTemplate<Integer, T> kafkaTemplate;
    private final KafkaTopicConfig kafkaTopicConfig;

    @Override
    public void send(T message) {
        kafkaTemplate.send(message.getTopic(kafkaTopicConfig), message);
    }
}
