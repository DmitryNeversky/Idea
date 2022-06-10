package org.dneversky.gateway.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserRabbitMQConfig {

    public static final String RPC_EXCHANGE = "rpc_exchange";
    public static final String RPC_GET_USERS = "rpc.get.users";
    public static final String RPC_GET_USER_BY_ID = "rpc.get.user.byId";
    public static final String RPC_GET_USER_BY_USERNAME = "rpc.get.user.byUsername";
    public static final String RPC_SAVE_USER = "rpc.save.user";

    @Bean
    public DirectExchange rpcExchange() {
        return new DirectExchange(RPC_EXCHANGE);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}