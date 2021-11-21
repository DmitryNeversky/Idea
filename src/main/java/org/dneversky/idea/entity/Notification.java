package org.dneversky.idea.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@NoArgsConstructor
@Getter
@Setter
public class Notification {

    @Id
    private String id;

    private String title;
    private String message;
    @JsonFormat(pattern = "dd-MM-yy HH:mm:SS")
    private LocalDateTime createdDate;

    public Notification(String title, String message, LocalDateTime createdDate) {
        this.title = title;
        this.message = message;
        this.createdDate = createdDate;
    }
}
