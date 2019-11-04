package com.dousnl.config;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * TODO
 *
 * @author hanliang
 * @version 1.0
 * @date 2019/11/1 15:48
 */
@Component
@RabbitListener(queues="topic.man")
public class TopicManReceiver {

    @RabbitHandler
    public void Receiver(Map message){
        System.out.println("TopicManReceiver消费者收到消息  : " + message.toString());
    }
}
