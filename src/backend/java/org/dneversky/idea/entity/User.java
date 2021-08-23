package org.dneversky.idea.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String email;
    private String password;

    private String name;
    private String phone;
    private String post;
    private LocalDate birthday;
    private LocalDate registeredDate;

    @OneToMany(mappedBy = "author")
    private Set<Idea> ideas;

    public User() {}

    public User(String email, String password, String name, String phone, String post, LocalDate birthday, LocalDate registeredDate) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.post = post;
        this.birthday = birthday;
        this.registeredDate = registeredDate;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public LocalDate getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(LocalDate registeredDate) {
        this.registeredDate = registeredDate;
    }

    public Set<Idea> getIdeas() {
        return ideas;
    }

    public void setIdeas(Set<Idea> ideas) {
        this.ideas = ideas;
    }

    public void addIdea(Idea idea) {
        this.ideas.add(idea);
    }
}
