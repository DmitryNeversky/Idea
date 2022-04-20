package org.dneversky.emailer.service;

import org.dneversky.emailer.listener.EmailListener;
import org.dneversky.emailer.model.EmailNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    Logger logger = LoggerFactory.getLogger(EmailListener.class);

    @Value("${spring.mail.username}")
    private String username;

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMessage(EmailNotification emailNotification) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(username);
        message.setTo(emailNotification.getMailTo());
        message.setSubject(emailNotification.getSubject());
        message.setText(emailNotification.getMessage());

        logger.info("Sending message from: {}, to: {}, subject: {}, text: {}.",
                message.getFrom(), message.getTo(), message.getSubject(), message.getText());

        javaMailSender.send(message);
    }
}