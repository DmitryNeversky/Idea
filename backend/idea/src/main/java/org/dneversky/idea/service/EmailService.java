package org.dneversky.idea.service;

public interface EmailService {
    void send(String to, String subject, String text);
}
