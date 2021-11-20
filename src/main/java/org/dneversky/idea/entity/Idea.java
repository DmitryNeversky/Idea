package org.dneversky.idea.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dneversky.idea.model.Status;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

@Document
@Getter
@Setter
@NoArgsConstructor
public class Idea implements Serializable {

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

    @JsonIgnoreProperties("ideas")
    private List<Tag> tags = new ArrayList<>();

    private Set<String> images = new HashSet<>();

    private Map<String, String> files = new HashMap<>();

    @JsonIgnoreProperties({"ideas", "roles"})
    private User author;

    @JsonIgnore
    private Set<Long> lookedUsers = new HashSet<>();

    @JsonIgnoreProperties("ideas")
    private Set<User> ratedUsers = new HashSet<>();

    @JsonIgnoreProperties("ideas")
    private Set<User> unratedUsers = new HashSet<>();

    // for the response body

    @JsonProperty("rating")
    public int getRating() {
        return ratedUsers.size() - unratedUsers.size();
    }

    @JsonProperty("looks")
    public int getLooks() {
        return lookedUsers.size();
    }

    public Idea(String title, String body, Status status, LocalDate createdDate, User author) {
        this.title = title;
        this.body = body;
        this.status = status;
        this.createdDate = createdDate;
        this.author = author;
    }
}
