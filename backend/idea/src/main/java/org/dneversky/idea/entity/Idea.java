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
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Idea implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String body;

    @Enumerated(EnumType.STRING)
    private Status status;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate createdDate;

    @JsonIgnoreProperties("ideas")
    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(
            name = "idea_tags",
            joinColumns = @JoinColumn(name = "idea_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "idea_image", joinColumns = @JoinColumn(name = "idea_id"))
    private Set<String> images = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "idea_file", joinColumns = {@JoinColumn(name = "idea_id")})
    @MapKeyColumn(name = "file_path")
    private Map<String, String> files = new HashMap<>();

    @JsonIgnoreProperties({"ideas", "roles"})
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    @JsonIgnore
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "idea_looked_users", joinColumns = @JoinColumn(name = "idea_id"))
    @JoinColumn(name = "user_id")
    private Set<Long> lookedUsers = new HashSet<>();

    @JsonIgnoreProperties("ideas")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Set<User> ratedUsers = new HashSet<>();

    @JsonIgnoreProperties("ideas")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
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
