package org.dneversky.user.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Post implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Size(max = 128, message = "Name size is: min 0 max 128")
    private String name;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private Set<User> users = new HashSet<>();

    public Post(String name) {
        this.name = name;
    }
}
