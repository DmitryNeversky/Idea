package org.dneversky.emailer.model;

import lombok.Data;

@Data
public class EmailMessage {
    private String mailTo;
    private String subject;
    private String message;
}
