package org.dneversky.gateway.servie.impl;

import org.dneversky.gateway.model.User;
import org.dneversky.gateway.repository.ReplicatedUserRepository;
import org.dneversky.gateway.security.UserPrincipal;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Service
public class UserServiceImpl {

    private ReplicatedUserRepository replicatedUserRepository;
    private RabbitTemplate rabbitTemplate;

    public UserPrincipal getUserPrincipal(String username) {
        UserPrincipal principal = rabbitTemplate.convertSendAndReceive("", "rpc");
    }
}
