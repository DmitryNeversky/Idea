package org.dneversky.idea.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("idea")
public class Idea {

    @Id
    private String id;

    private String title;
    private String text;

    private Status status;

    private int rating;
    private int looks;

    public Idea() {}

    public Idea(String id, String title, String text, Status status, int rating, int looks) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.status = status;
        this.rating = rating;
        this.looks = looks;
    }

    public Idea(String title, String text, Status status) {
        this.title = title;
        this.text = text;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
