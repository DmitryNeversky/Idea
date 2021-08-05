package org.dneversky.idea.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash("Idea")
public class Idea {

    @Id
    private String id;

    @Indexed
    private String title;
    private String text;

    private int rating;
    private int looks;

    public Idea() {}

    public Idea(String title, String text) {
        this.title = title;
        this.text = text;
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

    @Override
    public String toString() {
        return "Idea{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", rating=" + rating +
                ", looks=" + looks +
                '}';
    }
}
