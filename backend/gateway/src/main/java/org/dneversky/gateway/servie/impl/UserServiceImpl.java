package org.dneversky.gateway.servie.impl;

import org.dneversky.gateway.repository.ReplicatedUserRepository;
import org.dneversky.gateway.security.UserPrincipal;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl {

    private ReplicatedUserRepository replicatedUserRepository;
    private RabbitTemplate rabbitTemplate;

    public UserPrincipal getUserPrincipal(String username) {
        return replicatedUserRepository.findByUsername(username).orElse(() -> {
            rabbitTemplate.send();
        });
    }
}
