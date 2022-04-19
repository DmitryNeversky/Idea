package org.dneversky.idea.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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
