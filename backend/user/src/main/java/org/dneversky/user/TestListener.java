package org.dneversky.user;

import lombok.RequiredArgsConstructor;
import org.dneversky.user.config.RabbitMQConfig;
import org.dneversky.user.entity.User;
import org.dneversky.user.model.UserReplyMessage;
import org.dneversky.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@EnableRabbit
@Component
public class TestListener {

    private static final Logger logger = LoggerFactory.getLogger(TestListener.class);

    private final UserRepository userRepository;
    private final RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = RabbitMQConfig.RECEIVE_QUEUE)
    public void doGet(UserReplyMessage message) {
        logger.info("Received message: {}", message);
        UserReplyMessage response = new UserReplyMessage();
        logger.info("Sending message: {}", response);
        rabbitTemplate.convertSendAndReceive(RabbitMQConfig.RPC_EXCHANGE, RabbitMQConfig.REPLY_QUEUE, response);

//        User user = userRepository.findByUsername(username).orElse(null);
//        System.out.println(user);
//        if(user != null) {
//            MessageProperties properties = new MessageProperties();
//            properties.setType("org.dneversky.gateway.model.UserResponse");
//            properties.setContentType("application/json");
//
//            String message = new UserReplyMessage(user.getId(), user.getUsername(), user.getPassword(), user.isEnabled()).toString();
//            return message;
//        }
//
//        return null;
    }
}
