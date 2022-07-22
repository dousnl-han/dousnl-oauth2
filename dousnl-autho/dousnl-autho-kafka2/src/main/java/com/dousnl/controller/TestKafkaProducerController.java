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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试kafka生产者
 */
@RestController
@RequestMapping("kafka")
public class TestKafkaProducerController {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @RequestMapping(value = "send",method=RequestMethod.POST)
    public String send(final String msg){
        for (int i=0;i<10;i++){
            Thread t=new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i=0;i<10000;i++){
                        kafkaTemplate.send("test_topic", msg);
                    }
                }
            });
            t.start();
        }
        return "success";
    }

}
