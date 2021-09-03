package org.dneversky.idea.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "idea_tag", joinColumns = @JoinColumn(name = "idea_id"))
    private Set<String> tags;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "idea_image", joinColumns = @JoinColumn(name = "idea_id"))
    private Set<String> images;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "idea_file", joinColumns = {@JoinColumn(name = "idea_id")})
    @MapKeyColumn(name = "file_path")
    private Map<String, String> files;

    @ManyToOne(cascade = { CascadeType.REFRESH, CascadeType.DETACH }, fetch = FetchType.EAGER)
    private User author;

    // Need a Redis storage

    private int looks;

    @Getter(AccessLevel.NONE)
    private int rating;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<User> lookedUsers;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<User> ratedUsers = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<User> unratedUsers = new HashSet<>();

    // ---

    @Transient
    private List<String> removeImages;
    @Transient
    private List<String> removeFiles;

    public void addImage(String image) {
        if(this.images == null) {
            this.images = new HashSet<>();
        } this.images.add(image);
    }

    public void addFile(String key, String value) {
        if(this.files == null) {
            this.files = new HashMap<>();
        } this.files.put(key, value);
    }

    public void addLook(User user) {
        if(this.lookedUsers == null) {
            this.lookedUsers = new HashSet<>();
        } this.lookedUsers.add(user);
        this.looks++;
    }

    public int getRating() {
        return getRatedUsers().size() - getUnratedUsers().size();
    }
}
