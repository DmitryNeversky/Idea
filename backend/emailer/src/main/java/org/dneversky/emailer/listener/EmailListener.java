package org.dneversky.emailer.listener;

import org.dneversky.emailer.model.EmailNotification;
import org.dneversky.emailer.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@EnableRabbit
@Component
public class EmailListener {

    Logger logger = LoggerFactory.getLogger(EmailListener.class);

    @Autowired
    private EmailService emailService;

    @RabbitListener(queues = "emailQueue")
    public void receiveMessageOne(@Payload EmailNotification emailNotification) {
        logger.info("Received {}", emailNotification.toString());
        emailService.sendMessage(emailNotification);
    }

    @RabbitListener(queues = "emailQueue")
    public void receiveMessageTwo(@Payload EmailNotification emailNotification) {
        logger.info("Received {}", emailNotification.toString());
        emailService.sendMessage(emailNotification);
    }

    @RabbitListener(queues = "emailQueue")
    public void receiveMessageThree(@Payload EmailNotification emailNotification) {
        logger.info("Received {}", emailNotification.toString());
        emailService.sendMessage(emailNotification);
    }
}