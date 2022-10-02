package org.dneversky.emailer.model;

import java.util.Objects;

public class EmailNotification {

    private String mailTo;
    private String subject;
    private String message;

    public EmailNotification() {
    }

    public EmailNotification(String mailTo, String subject, String message) {
        this.mailTo = mailTo;
        this.subject = subject;
        this.message = message;
    }

    public String getMailTo() {
        return mailTo;
    }

    public void setMailTo(String mailTo) {
        this.mailTo = mailTo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmailNotification that = (EmailNotification) o;
        return Objects.equals(mailTo, that.mailTo) && Objects.equals(subject, that.subject) && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mailTo, subject, message);
    }

    @Override
    public String toString() {
        return "EmailNotification{" +
                "mailTo='" + mailTo + '\'' +
                ", subject='" + subject + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}