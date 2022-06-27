package org.dneversky.emailer.service.impl;

import org.dneversky.emailer.model.MimeMessage;
import org.dneversky.emailer.service.MessageSender;
import org.springframework.stereotype.Component;

@Component
public class MimeMessageSender implements MessageSender<MimeMessage> {

    @Override
    public void send(MimeMessage emailMessage) {

    }
}
