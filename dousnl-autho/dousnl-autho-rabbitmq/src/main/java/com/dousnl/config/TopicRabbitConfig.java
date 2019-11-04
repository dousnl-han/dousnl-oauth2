package com.dousnl.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author hanliang
 * @version 1.0
 * @date 2019/10/31 18:31
 */
@Configuration
public class TopicRabbitConfig {

    public static final String man = "topic.man";
    public static final String women = "topic.women";

    @Bean
    public Queue firstQueue() {
        return new Queue(TopicRabbitConfig.man);
    }

    @Bean
    public Queue secondQueue() {
        return new Queue(TopicRabbitConfig.women);
    }

    @Bean
    public TopicExchange TopicExchange() {
        return new TopicExchange("topicExchange");
    }

    @Bean
    Binding bindingExchangeMessage() {
        return BindingBuilder.bind(firstQueue()).to(TopicExchange()).with("topic.man");
    }

    @Bean
    Binding bindingExchangeMessage2() {
        return BindingBuilder.bind(secondQueue()).to(TopicExchange()).with("topic.#");
    }
}
