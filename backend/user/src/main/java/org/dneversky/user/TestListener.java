package org.dneversky.user;

import org.dneversky.user.config.RabbitMQConfig;
import org.dneversky.user.entity.User;
import org.dneversky.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TestListener {

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(TestListener.class);

    @RabbitListener(queues = RabbitMQConfig.RPC_GET_USERS)
    public List<User> rpcGetUsers() {
        List<User> users = userRepository.findAll();
        logger.info("Sending message: {}", users);
        return users;
    }

    @RabbitListener(queues = RabbitMQConfig.RPC_GET_USER_BY_ID)
    public User rpcGetUserById(Long id) {
        logger.info("Received message: {}", id);
        User user = userRepository.findById(id).orElse(null);
        logger.info("Sending message: {}", user);
        return user;
    }

    @RabbitListener(queues = RabbitMQConfig.RPC_GET_USER_BY_USERNAME)
    public User rpcGetUserByUsername(String username) {
        logger.info("Received message: {}", username);
        User user = userRepository.findByUsername(username).orElse(null);
        logger.info("Sending message: {}", user);
        return user;
    }

    @RabbitListener(queues = RabbitMQConfig.RPC_SAVE_USER)
    public User rpcSaveUser(User user) {
        logger.info("Received message: {}", user);
        User newUser = userRepository.save(user);
        logger.info("Sending message: {}", newUser);
        return user;
    }
}
