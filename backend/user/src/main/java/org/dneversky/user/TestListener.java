package org.dneversky.user;

import org.dneversky.user.entity.User;
import org.dneversky.user.repository.UserRepository;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@EnableRabbit
@Component
public class TestListener {

    private final UserRepository userRepository;

    public TestListener(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RabbitListener(queues = "userQueue")
    public User doGet(String username) {
        System.out.println(username);

        System.out.println(userRepository.findByUsername(username).orElse(null));

        return null;
    }
}
