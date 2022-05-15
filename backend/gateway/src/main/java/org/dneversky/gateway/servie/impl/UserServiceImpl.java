package org.dneversky.gateway.servie.impl;

import org.dneversky.gateway.security.UserPrincipal;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public UserPrincipal getUserByUsername(String username) {
        Message receivedMessage = (Message) rabbitTemplate.convertSendAndReceive("rpc_exchange", "rpc", username);

        return null;
    }
}
