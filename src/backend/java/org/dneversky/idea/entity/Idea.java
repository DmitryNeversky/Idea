package org.dneversky.idea.entity;

import org.dneversky.idea.model.Status;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Entity
public class Idea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String text;

    private Status status;

    private int rating;
    private int looks;

    private LocalDateTime createdDate;

    private Set<String> tags;
    private Set<String> images;
    private Map<String, String> files = new HashMap<>();

    @ManyToOne
    @JoinColumn(name = "idea_id", nullable = false)
    private User author;

    public Idea() {}

    public Idea(String title, String text, Status status, LocalDateTime createdDate, User author) {
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

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
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

    public void addImage(String image) {
        getImages().add(image);
    }

    public void addFile(String key, String value) {
        getFiles().put(key, value);
    }
}
