package org.dneversky.notifier.model;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    private String title;
    private String text;
    private LocalDateTime localDateTime;
}
