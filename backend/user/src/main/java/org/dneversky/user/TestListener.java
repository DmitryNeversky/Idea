package org.dneversky.user;

import org.dneversky.user.entity.User;
import org.dneversky.user.model.UserReplyMessage;
import org.dneversky.user.repository.UserRepository;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.stereotype.Component;

@EnableRabbit
@Component
public class TestListener {

    private final UserRepository userRepository;

    public TestListener(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RabbitListener(queues = "userQueue", messageConverter = "jsonMessageConverter")
    public UserReplyMessage doGet(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        if(user != null) {

            return new UserReplyMessage(user.getId(), user.getUsername(), user.getPassword(), user.isEnabled());
        }

        return null;
    }
}
