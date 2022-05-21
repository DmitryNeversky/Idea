package org.dneversky.user.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String RECEIVE_QUEUE = "rpc_queue";
    public static final String REPLY_QUEUE = "reply_queue";
    public static final String RPC_EXCHANGE = "rpc_exchange";

    @Bean
    public TopicExchange rpcExchange() {
        return new TopicExchange(RPC_EXCHANGE);
    }

    @Bean
    public Queue receiveQueue() {
        return new Queue(RECEIVE_QUEUE);
    }

    @Bean
    public Queue replyQueue() {
        return new Queue(REPLY_QUEUE);
    }

    @Bean
    public Binding receiveBinding() {
        return BindingBuilder.bind(receiveQueue()).to(rpcExchange()).with(RECEIVE_QUEUE);
    }

    @Bean
    public Binding replyBinding() {
        return BindingBuilder.bind(replyQueue()).to(rpcExchange()).with(REPLY_QUEUE);
    }

//    @Bean
//    public Jackson2JsonMessageConverter jsonMessageConverter() {
//        return new Jackson2JsonMessageConverter();
//    }
}
