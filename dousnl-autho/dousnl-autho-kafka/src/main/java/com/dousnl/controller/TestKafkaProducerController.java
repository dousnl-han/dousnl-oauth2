package com.dousnl.controller;

/**
 * TODO
 *
 * @author hanliang
 * @version 1.0
 * @date 2019/9/5 13:37
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试kafka生产者
 */
@RestController
@RequestMapping("kafka")
public class TestKafkaProducerController {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @RequestMapping("send")
    public String send(String msg){
        kafkaTemplate.send("test_topic", msg);
        return "success";
    }

}
