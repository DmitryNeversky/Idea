package org.dneversky.user.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String RPC_EXCHANGE = "rpc_exchange";
    public static final String RPC_USER_QUEUE = "rpcUserQueue";
    public static final String RPC_USERS_QUEUE = "rpcUsersQueue";

    @Bean
    public DirectExchange rpcExchange() {
        return new DirectExchange(RPC_EXCHANGE);
    }

    @Bean
    public Queue rpcUserQueue() {
        return new Queue(RPC_USER_QUEUE);
    }

    @Bean
    public Queue rpcUsersQueue() {
        return new Queue(RPC_USERS_QUEUE);
    }

    @Bean
    public Binding rpcUserQueueBinding() {
        return BindingBuilder.bind(rpcUserQueue()).to(rpcExchange()).with(rpcUserQueue().getName());
    }

    @Bean
    public Binding rpcUsersQueueBinding() {
        return BindingBuilder.bind(rpcUsersQueue()).to(rpcExchange()).with(rpcUsersQueue().getName());
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
