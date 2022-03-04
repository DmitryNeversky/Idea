 package org.dneversky.idea.service.impl;

 import org.dneversky.idea.model.EmailNotification;
 import org.dneversky.idea.service.EmailService;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.beans.factory.annotation.Value;
 import org.springframework.kafka.core.KafkaTemplate;
 import org.springframework.kafka.support.KafkaHeaders;
 import org.springframework.messaging.Message;
 import org.springframework.messaging.support.MessageBuilder;
 import org.springframework.stereotype.Service;

 @Service
 public class EmailServiceImpl implements EmailService {

     @Value("${kafka.emailTopic}")
     private String emailTopic;

     @Autowired
     private KafkaTemplate<String, EmailNotification> emailNotificationKafkaTemplate;

     @Override
     public void send(String to, String subject, String text) {
         Message<EmailNotification> message = MessageBuilder
                 .withPayload(new EmailNotification(to, subject, text))
                 .setHeader(KafkaHeaders.TOPIC, emailTopic)
                 .build();

         emailNotificationKafkaTemplate.send(message);
     }
 }
