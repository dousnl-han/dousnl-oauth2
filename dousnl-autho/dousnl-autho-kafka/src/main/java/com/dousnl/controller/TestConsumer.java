package com.dousnl.controller;

/**
 * TODO
 *
 * @author hanliang
 * @version 1.0
 * @date 2019/9/5 13:37
 */

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * kafka消费者测试
 */
@Component
public class TestConsumer {

    @KafkaListener(topics = "test_topic")
    public void listen (ConsumerRecord<?, ?> record) throws Exception {
        System.out.printf("消费者>>>topic = %s, offset = %d, value = %s \n", record.topic(), record.offset(), record.value());
    }
}
