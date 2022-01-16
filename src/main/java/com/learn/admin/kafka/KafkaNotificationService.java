package com.learn.admin.kafka;

import com.learn.admin.kafka.dto.TopicAware;

public interface KafkaProducerService<T extends TopicAware> {
    void send(T message);
}
