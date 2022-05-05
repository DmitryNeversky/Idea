package org.dneversky.idea.agregate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.*;
import org.dneversky.idea.model.Status;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.*;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Idea {

    @Id
    private Long id;

    private String title;
    private String body;
    private Status status;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate createdDate;

    @DBRef
    private List<Long> tags = new ArrayList<>();

    private Set<String> images = new HashSet<>();
    private Map<String, String> files = new HashMap<>();

    @DBRef
    private Long authorId;

    private Set<Long> lookedUsers = new HashSet<>();
    private Set<Long> ratedUsers = new HashSet<>();
    private Set<Long> unratedUsers = new HashSet<>();

    @JsonProperty("rating")
    public int getRating() {
        return ratedUsers.size() - unratedUsers.size();
    }

    @JsonProperty("looks")
    public int getLooks() {
        return lookedUsers.size();
    }
}
