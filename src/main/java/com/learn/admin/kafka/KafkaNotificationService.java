package com.learn.admin.kafka;

import com.learn.admin.kafka.dto.TopicAware;

public interface KafkaNotificationService<T extends TopicAware> {
    void send(T message);
}
