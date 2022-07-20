package org.dneversky.gateway.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveUserRequest {

    private String username;
    private String password;
    private String name;
    private String phone;
    private Date birthday;
    private Post post;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SaveUserRequest that = (SaveUserRequest) o;
        return Objects.equals(username, that.username) && Objects.equals(password, that.password) && Objects.equals(name, that.name) && Objects.equals(phone, that.phone) && Objects.equals(birthday, that.birthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, name, phone, birthday);
    }
}
