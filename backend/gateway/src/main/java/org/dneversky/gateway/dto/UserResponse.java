package org.dneversky.gateway.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dneversky.gateway.model.Post;
import org.dneversky.gateway.model.Role;
import org.dneversky.gateway.model.User;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private long id;
    private String username;
    private String name;
    private String phone;
    private LocalDate birthday;
    private String avatar;
    private String city;
    private String about;
    private LocalDate registeredDate;
    private Post post;
    private Set<Role> roles;

    public UserResponse mergeFromUser(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .roles(user.getRoles())
                .name(user.getName())
                .phone(user.getPhone())
                .birthday(birthday)
                .avatar(user.getAvatar())
                .city(user.getCity())
                .about(user.getAbout())
                .registeredDate(registeredDate)
                .post(user.getPost())
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserResponse that = (UserResponse) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
