package org.dneversky.idea.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dneversky.idea.model.Status;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    private Integer id;

    @NotNull(message = "Title can not be null")
    @Size(min = 8, max = 256, message = "Title's size is: min 8 max 256")
    private String title;

    @Lob
    @NotNull(message = "Text can not be null")
    @Size(min = 256, max = 32768, message = "Text's size is: min 256 max 32768")
    private String text;

    @Enumerated(EnumType.STRING)
    private Status status;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate createdDate;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private List<Tag> tags = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "idea_image", joinColumns = @JoinColumn(name = "idea_id"))
    private Set<String> images = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "idea_file", joinColumns = {@JoinColumn(name = "idea_id")})
    @MapKeyColumn(name = "file_path")
    private Map<String, String> files = new HashMap<>();

    @JsonIgnoreProperties("ideas")
    @ManyToOne(cascade = { CascadeType.DETACH, CascadeType.PERSIST })
    @JoinTable(name = "idea_user", joinColumns = @JoinColumn(name = "idea_id"))
    private User author;

    // Need a Redis storage

    @Getter(AccessLevel.NONE)
    private int looks;

    @Getter(AccessLevel.NONE)
    private int rating;

    @JsonIgnore
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "idea_looked_users", joinColumns = @JoinColumn(name = "idea_id"))
    private Set<Integer> lookedUsers = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<User> ratedUsers = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<User> unratedUsers = new HashSet<>();

    // ---

    @Transient
    private List<String> removeImages = new ArrayList<>();
    @Transient
    private List<String> removeFiles = new ArrayList<>();

    public int getRating() {
        return ratedUsers.size() - unratedUsers.size();
    }

    public int getLooks() {
        return lookedUsers.size();
    }
}
