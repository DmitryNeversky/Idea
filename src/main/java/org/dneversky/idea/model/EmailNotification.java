package org.dneversky.idea.model;

public record EmailNotification(String mailTo, String subject, String message) {
    public EmailNotification {}
}
