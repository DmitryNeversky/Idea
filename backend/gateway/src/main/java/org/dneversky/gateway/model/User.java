package org.dneversky.gateway.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dneversky.gateway.UserServiceOuterClass;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;
    private String username;
    private String password;
    private Set<Role> roles;
    private boolean enabled;

    private String name;
    private String phone;
    private Date birthday;
    private String avatar;
    private String city;
    private String about;
    private LocalDate registeredDate;
    private Post post;

    public static User buildUser(UserServiceOuterClass.User user) {
        return new User(user.getId(), user.getUsername(), user.getPassword(),
                user.getRolesList().stream()
                        .map(e -> new Role(e.getId(), e.getName()))
                        .collect(Collectors.toSet()), user.getEnabled(),
                Optional.of(user.getName()).orElse(""),
                Optional.of(user.getPhone()).orElse(""),
                new Date(),
                Optional.of(user.getAvatar()).orElse(""),
                Optional.of(user.getCity()).orElse(""),
                Optional.of(user.getAbout()).orElse(""),
                LocalDate.parse(user.getRegisteredDate()),
                new Post(user.getPost().getId(), user.getPost().getName()));
    }
}
