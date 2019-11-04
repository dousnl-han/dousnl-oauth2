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
 * @date 2019/10/31 17:27
 */
@Component
@RabbitListener(queues = "testRabbitmqQueue")
public class MessageConsumer {

    @RabbitHandler
    public void processMessage(Map message) {
        System.out.println("MessageConsumer收到消息："+ message.toString());
    }
}
