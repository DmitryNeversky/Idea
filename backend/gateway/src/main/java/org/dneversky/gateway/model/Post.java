package org.dneversky.gateway.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    private int id;
    private String name;
    private Set<User> users = new HashSet<>();

    public Post(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
