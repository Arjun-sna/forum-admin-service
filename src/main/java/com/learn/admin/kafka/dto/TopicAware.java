package com.learn.admin.kafka.dto;

import com.learn.admin.kafka.KafkaTopicConfig;

public interface TopicAware {
    String getTopic(KafkaTopicConfig kafkaTopicConfig);
}
