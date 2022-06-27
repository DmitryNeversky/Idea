package org.dneversky.emailer.service;

import org.dneversky.emailer.model.SimpleMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmailListener {

    private final Logger logger = LoggerFactory.getLogger(EmailListener.class);
    private final MessageSender<SimpleMessage> simpleMessageMessageSender;

    @Autowired
    public EmailListener(MessageSender<SimpleMessage> simpleMessageMessageSender) {
        this.simpleMessageMessageSender = simpleMessageMessageSender;
    }

    @RabbitListener(queues = "q.email")
    public void receiveMessageOne(@Payload SimpleMessage simpleMessage) {
        logger.info("Received {}", simpleMessage.toString());
        simpleMessageMessageSender.send(simpleMessage);
    }
}