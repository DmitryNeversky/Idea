package org.dneversky.idea.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    private String title;
    private String text;
    private Long recipientId;
}
