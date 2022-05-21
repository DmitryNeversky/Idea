package org.dneversky.gateway.servie.impl;

import org.dneversky.gateway.config.RabbitMQConfig;
import org.dneversky.gateway.security.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public UserPrincipal getUserByUsername(String username) {
        Message message = MessageBuilder.withBody(username.getBytes()).build();
        logger.info("Sending created message: {}", message);
        Message result = rabbitTemplate.sendAndReceive(RabbitMQConfig.RPC_EXCHANGE, RabbitMQConfig.RPC_QUEUE, message);
        logger.info("Getting response: {}", result);

        return null;
    }
}
