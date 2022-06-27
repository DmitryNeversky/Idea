package org.dneversky.emailer.service.impl;

import org.dneversky.emailer.model.SimpleMessage;
import org.dneversky.emailer.service.EmailListener;
import org.dneversky.emailer.service.MessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class SimpleMessageSender implements MessageSender<SimpleMessage> {

    @Value("${spring.mail.username}")
    private String username;

    private final Logger logger = LoggerFactory.getLogger(EmailListener.class);
    private final JavaMailSender javaMailSender;

    @Autowired
    public SimpleMessageSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void send(SimpleMessage simpleMessage) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(username);
        message.setTo(simpleMessage.getMailTo());
        message.setSubject(simpleMessage.getSubject());
        message.setText(simpleMessage.getMessage());

        logger.info("Sending message from: {}, to: {}, subject: {}, text: {}.",
                message.getFrom(), message.getTo(), message.getSubject(), message.getText());

        javaMailSender.send(message);
    }
}
