package org.dneversky.idea.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NaturalId
    @NotNull(message = "Username can not be null")
    @Email(message = "Email is invalid")
    @Size(min = 3, max = 64, message = "Username's size is: min 3 max 64")
    private String username;

    @NotNull(message = "Password can not be null")
    @Size(min = 6, message = "Password's size is: min 6")
    @NotBlank(message = "Password contains whitespaces or null value")
    @Lob
    private String password;

    @NotNull(message = "Name can not be null")
    @Size(min = 6, max = 96, message = "Name's size is: min 6 max 96")
    private String name;

    @NotNull(message = "Phone can not be null")
    @Size(min = 11, max = 11, message = "Phone's size is: min 11 max 11")
    @NotBlank(message = "Phone contains whitespaces or null value")
    private String phone;

    @NotNull(message = "Post can not be null")
    @Size(max = 96, message = "Post size is: min 0 max 96")
    private String post;

    @NotNull(message = "Birthday can not be null")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate birthday;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate registeredDate;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = {
            CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST
    })
    private Set<Idea> ideas;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();

    public User(String username, String password, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public void addIdea(Idea idea) {
        if(this.ideas == null) {
            this.ideas = new HashSet<>();
        } this.ideas.add(idea);
    }
}
