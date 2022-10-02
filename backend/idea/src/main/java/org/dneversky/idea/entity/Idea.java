package org.dneversky.idea.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dneversky.idea.model.Status;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
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
    @CollectionTable(name = "idea_image", joinColumns = {@JoinColumn(name = "idea_id")})
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
    @JoinTable(
            name = "idea_rated_users",
            joinColumns = {@JoinColumn(name = "idea_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private Set<User> ratedUsers = new HashSet<>();

    @JsonIgnoreProperties("ideas")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "idea_unrated_users",
            joinColumns = {@JoinColumn(name = "idea_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private Set<User> unratedUsers = new HashSet<>();

    @JsonProperty("rating")
    public int getRating() {
        return ratedUsers.size() - unratedUsers.size();
    }

    @JsonProperty("looks")
    public int getLooks() {
        return lookedUsers.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Idea idea = (Idea) o;
        return Objects.equals(id, idea.id) && Objects.equals(title, idea.title) && Objects.equals(body, idea.body) && status == idea.status && Objects.equals(createdDate, idea.createdDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, body, status, createdDate);
    }

    @Override
    public String toString() {
        return "Idea{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", status=" + status +
                ", createdDate=" + createdDate +
                '}';
    }
}
