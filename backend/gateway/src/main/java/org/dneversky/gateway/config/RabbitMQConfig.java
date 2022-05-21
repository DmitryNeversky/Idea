package org.dneversky.gateway.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String RPC_QUEUE = "rpc_queue";
    public static final String REPLY_QUEUE = "reply_queue";
    public static final String RPC_EXCHANGE = "rpc_exchange";

    @Bean
    public Queue rpcQueue() {
        return new Queue(RPC_QUEUE);
    }

    @Bean
    public Queue replyQueue() {
        return new Queue(REPLY_QUEUE);
    }

    @Bean
    public TopicExchange rpcExchange() {
        return new TopicExchange(RPC_EXCHANGE);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setExchange(RPC_EXCHANGE);
        rabbitTemplate.setReplyAddress(REPLY_QUEUE);
        rabbitTemplate.setReplyTimeout(6000);

        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public SimpleMessageListenerContainer replyContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(REPLY_QUEUE);
        container.setMessageListener(rabbitTemplate(connectionFactory));
        return container;
    }
}