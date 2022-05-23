package org.dneversky.gateway.servie.impl;

import org.dneversky.gateway.config.RabbitMQConfig;
import org.dneversky.gateway.model.UserRequest;
import org.dneversky.gateway.model.UserResponse;
import org.dneversky.gateway.security.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public UserPrincipal getUserByUsername(String username) {
        UserRequest request = new UserRequest(username);
        logger.info("Sending created message: {}", request);
        UserResponse response = rabbitTemplate.convertSendAndReceiveAsType(
                RabbitMQConfig.RPC_EXCHANGE, RabbitMQConfig.ROUTING_KEY, request,
                new ParameterizedTypeReference<UserResponse>() {});
        logger.info("Getting response: {}", response);

        return null;
    }
}
