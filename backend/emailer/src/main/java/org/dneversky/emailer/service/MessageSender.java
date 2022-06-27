package org.dneversky.emailer.service;

import org.dneversky.emailer.model.EmailMessage;

public interface MessageSender<T extends EmailMessage> {
    void send(T emailMessage);
}