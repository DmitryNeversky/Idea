package org.dneversky.gateway.servie.impl;

import org.dneversky.gateway.security.UserPrincipal;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public UserPrincipal getUserByUsername(String username) {
        UserPrincipal response = (UserPrincipal) rabbitTemplate.convertSendAndReceive("rpc_exchange", "rpc", username);

        System.out.println(response);

        return response;
    }
}
