package com.learn.admin.kafka;

public interface KafkaProducerService<T> {
    void send(T message);
}
