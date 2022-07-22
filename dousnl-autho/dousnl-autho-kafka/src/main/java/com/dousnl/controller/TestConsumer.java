package com.dousnl.controller;

/**
 * TODO
 *
 * @author hanliang
 * @version 1.0
 * @date 2019/9/5 13:37
 */

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.autoconfigure.jms.JmsProperties;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import scala.util.parsing.json.JSON;

import java.util.List;

/**
 * kafka消费者测试
 */
@Component
public class TestConsumer {

    @KafkaListener(topics = "test_topic",group = "test1")
    public void listen (ConsumerRecord<?, ?> record, Acknowledgment ack) throws Exception {
        try{
            System.out.printf("消费者-1>>>topic = %s, partition = %d, offset = %d, value = %s \n", record.topic(),record.partition(), record.offset(), record.value());
            ack.acknowledge();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @KafkaListener(topics = "test_topic",group = "test2")
    public void listen (List<ConsumerRecord<?, ?>> records, Acknowledgment ack) throws Exception {
        try{
            for (ConsumerRecord record : records){
                System.out.printf("消费者-1>>>topic = %s, partition = %d, offset = %d, value = %s \n", record.topic(),record.partition(), record.offset(), record.value());
            }
            ack.acknowledge();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @KafkaListener(topics = "test_topic",group = "test")
    public void listen (String data) throws Exception {
        try{
            System.out.println(data);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
