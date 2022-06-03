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
    public static final String RPC_GET_USERS = "rpc.get.users";
    public static final String RPC_GET_USER_BY_ID = "rpc.get.user.byId";
    public static final String RPC_GET_USER_BY_USERNAME = "rpc.get.user.byUsername";

    @Bean
    public DirectExchange rpcExchange() {
        return new DirectExchange(RPC_EXCHANGE);
    }

    @Bean
    public Queue rpcGetUsers() {
        return new Queue(RPC_GET_USERS);
    }

    @Bean
    public Queue rpcGetUserById() {
        return new Queue(RPC_GET_USER_BY_ID);
    }

    @Bean
    public Queue rpcGetUserByUsername() {
        return new Queue(RPC_GET_USER_BY_USERNAME);
    }

    @Bean
    public Binding rpcGetUsersBinding() {
        return BindingBuilder.bind(rpcGetUsers()).to(rpcExchange()).with(rpcGetUsers().getName());
    }

    @Bean
    public Binding rpcGetUserByIdBinding() {
        return BindingBuilder.bind(rpcGetUserById()).to(rpcExchange()).with(rpcGetUserById().getName());
    }

    @Bean
    public Binding rpcGetUserByUsernameBinding() {
        return BindingBuilder.bind(rpcGetUserByUsername()).to(rpcExchange()).with(rpcGetUserByUsername().getName());
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
