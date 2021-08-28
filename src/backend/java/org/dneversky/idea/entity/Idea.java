package org.dneversky.idea.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.dneversky.idea.model.Status;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Idea {

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

    private int rating = 0;
    private int looks = 0;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate createdDate;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "idea_tag", joinColumns = @JoinColumn(name = "idea_id"))
    private Set<String> tags = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "idea_image", joinColumns = @JoinColumn(name = "idea_id"))
    private Set<String> images = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "idea_file", joinColumns = {@JoinColumn(name = "idea_id")})
    @MapKeyColumn(name = "file_path")
    private Map<String, String> files = new HashMap<>();

    @JsonBackReference
    @ManyToOne(cascade = { CascadeType.REFRESH, CascadeType.DETACH }, fetch = FetchType.EAGER)
    @JoinTable(name = "user_idea", joinColumns = @JoinColumn(name = "idea_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private User author;

    @Transient
    private List<String> removeImages;
    @Transient
    private List<String> removeFiles;

    public Idea(String title, String text, Status status, LocalDate createdDate, User author) {
        this.title = title;
        this.text = text;
        this.status = status;
        this.createdDate = createdDate;
        this.author = author;
    }

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
}
