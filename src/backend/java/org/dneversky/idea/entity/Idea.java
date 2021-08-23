package org.dneversky.idea.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.dneversky.idea.model.Status;

import javax.persistence.*;
import java.util.*;

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

    public Idea() {}

    public Idea(String title, String text, Status status, Date createdDate, User author) {
        this.title = title;
        this.text = text;
        this.status = status;
        this.createdDate = createdDate;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getLooks() {
        return looks;
    }

    public void setLooks(int looks) {
        this.looks = looks;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public Set<String> getImages() {
        return images;
    }

    public void setImages(Set<String> images) {
        this.images = images;
    }

    public Map<String, String> getFiles() {
        return files;
    }

    public void setFiles(Map<String, String> files) {
        this.files = files;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void addTag(String tag) {
        if(this.tags == null) {
            this.tags = new HashSet<>();
        } this.tags.add(tag);
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
