package org.dneversky.user;

import org.dneversky.user.config.RabbitMQConfig;
import org.dneversky.user.model.UserReplyMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TestListener {

    private static final Logger logger = LoggerFactory.getLogger(TestListener.class);

    @RabbitListener(queues = RabbitMQConfig.RECEIVE_QUEUE)
    public UserReplyMessage doGet(UserReplyMessage message) {
        logger.info("Received message: {}", message);
        UserReplyMessage response = new UserReplyMessage();
        logger.info("Sending message: {}", response);
        return response;
    }
}
