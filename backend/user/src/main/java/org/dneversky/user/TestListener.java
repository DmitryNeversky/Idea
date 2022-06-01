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

    @RabbitListener(queues = RabbitMQConfig.RECEIVE_QUEUE)
    public User getUserByUsername(String username) {
        logger.info("Received message: {}", username);
        User user = userRepository.findByUsername(username).orElse(null);
        logger.info("Sending message: {}", user);
        return user;
    }

    @RabbitListener(queues = RabbitMQConfig.RECEIVE_QUEUE)
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        logger.info("Sending message: {}", users);
        return users;
    }
}
