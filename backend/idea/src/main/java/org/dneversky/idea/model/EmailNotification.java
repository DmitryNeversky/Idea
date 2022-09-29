package org.dneversky.idea.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmailNotification {

    private String mailTo;
    private String subject;
    private String message;
}
