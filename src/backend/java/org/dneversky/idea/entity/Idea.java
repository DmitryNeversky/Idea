package org.dneversky.idea.entity;

import lombok.*;
import org.dneversky.idea.model.Status;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
public class Idea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String text;

    @Enumerated(EnumType.STRING)
    private Status status;

    private int rating;
    private int looks;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @ElementCollection
    @CollectionTable(name = "idea_tag", joinColumns = @JoinColumn(name = "idea_id"))
    @Column(name = "tag", nullable = false)
    private Set<String> tags;

    @ElementCollection
    @CollectionTable(name = "idea_image", joinColumns = @JoinColumn(name = "idea_id"))
    @Column(name = "image", nullable = false)
    private Set<String> images;

    @ElementCollection
    @CollectionTable(name = "idea_file", joinColumns = {@JoinColumn(name = "idea_id")})
    @MapKeyColumn(name = "file_path")
    @Column(name = "file_name", nullable = false)
    private Map<String, String> files;

    @ManyToOne
    @JoinColumn(name = "idea_id")
    private User author;

    public Idea(String title, String text, Status status, Date createdDate, User author) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Idea idea = (Idea) o;

        return Objects.equals(id, idea.id);
    }

    @Override
    public int hashCode() {
        return 1214633758;
    }
}
