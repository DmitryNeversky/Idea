package org.dneversky.idea.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dneversky.idea.model.Status;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Idea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Size(min = 8, max = 256)
    private String title;

    @NotNull
    @Size(min = 256, max = 32768)
    @Lob
    private String text;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull
    private int rating = 0;

    @NotNull
    private int looks = 0;

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

    @ManyToOne(cascade = CascadeType.DETACH)
    private User author;

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
