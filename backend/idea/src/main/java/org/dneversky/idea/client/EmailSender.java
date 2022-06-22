package org.dneversky.idea.client;

import org.dneversky.idea.model.EmailNotification;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(EmailNotification emailNotification) {
        rabbitTemplate.convertAndSend("e.messages", "msg", emailNotification);
    }
}
