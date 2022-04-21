package org.dneversky.notifier.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@RedisHash("Notification")
public class Notification implements Serializable {

    @Id
    private String id;

    private String title;
    private String text;

    @JsonFormat(pattern = "dd-MM-yy HH:mm:SS")
    private LocalDateTime createdDate;

    private Object recipientId;

    public Notification(String title, String text) {
        this.title = title;
        this.text = text;
        this.createdDate = LocalDateTime.now();
    }
}
