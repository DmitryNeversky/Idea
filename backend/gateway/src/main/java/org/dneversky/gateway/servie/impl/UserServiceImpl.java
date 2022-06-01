package org.dneversky.gateway.servie.impl;

import org.dneversky.gateway.config.RabbitMQConfig;
import org.dneversky.gateway.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public User getUserByUsername(String username) {
        logger.info("Sending created message: {}", username);
        User response = rabbitTemplate.convertSendAndReceiveAsType(
                RabbitMQConfig.RPC_EXCHANGE, RabbitMQConfig.ROUTING_KEY, username,
                new ParameterizedTypeReference<User>() {});
        logger.info("Getting response: {}", response);

        return response;
    }

    public List<User> getAllUsers() {
        List<User> response = rabbitTemplate.convertSendAndReceiveAsType(
                RabbitMQConfig.RPC_EXCHANGE, RabbitMQConfig.ROUTING_KEY, 1,
                new ParameterizedTypeReference<List<User>>() {});
        logger.info("Getting response: {}", response);

        return response;
    }
}
