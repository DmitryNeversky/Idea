package org.dneversky.gateway.servie.impl;

import org.dneversky.gateway.config.RabbitMQConfig;
import org.dneversky.gateway.model.UserResponse;
import org.dneversky.gateway.security.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public UserPrincipal getUserByUsername(String username) {
//        MessageProperties properties = new MessageProperties();
//        properties.setContentType("application/json");
//        properties.setHeader("__TypeId__", "org.dneversky.user.model");
//        properties.setContentEncoding("UTF-8");
//        messagePropertiesConverter.fromMessageProperties(properties, "UTF-8");
//        rabbitTemplate.setMessagePropertiesConverter(messagePropertiesConverter);
//        Message message = MessageBuilder.withBody(username.getBytes()).andProperties(properties).build();
        UserResponse message = new UserResponse("e@e");
        logger.info("Sending created message: {}", message);
        Object result = rabbitTemplate.convertSendAndReceive(RabbitMQConfig.RPC_EXCHANGE, RabbitMQConfig.RPC_QUEUE, message);
        logger.info("Getting response: {}", result);

        return null;
    }
}
