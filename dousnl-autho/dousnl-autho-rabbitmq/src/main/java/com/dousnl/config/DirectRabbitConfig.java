package com.dousnl.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * TODO
 *
 * @author hanliang
 * @version 1.0
 * @date 2019/10/31 16:50
 */
@Configuration
public class DirectRabbitConfig {

    @Bean
    public Queue TestQueue(){
        return new Queue("testRabbitmqQueue",true);
    }

    @Bean
    public DirectExchange TestExchange(){
        //return (DirectExchange) ExchangeBuilder.directExchange("TestDirectExchange").durable().build();
        return new DirectExchange("TestDirectExchange");
    }

    @Bean
    public Binding bindingDirect(){
        return BindingBuilder.bind(TestQueue()).to(TestExchange()).with("TestDirectRouting");
    }


}
