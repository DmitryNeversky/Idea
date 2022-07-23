package org.dneversky.gateway.converter;

import com.google.protobuf.Timestamp;
import org.dneversky.gateway.UserServiceOuterClass;
import org.dneversky.gateway.model.Role;
import org.dneversky.gateway.model.SaveUserRequest;
import org.dneversky.gateway.model.User;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

public final class UserConverter {

    public static UserServiceOuterClass.UserToSave convert(SaveUserRequest user) {
        Timestamp birthday = DateConverter.convert(user.getBirthday());

        return UserServiceOuterClass.UserToSave.newBuilder()
                .setUsername(user.getUsername())
                .setPassword(user.getPassword())
                .setName(user.getName())
                .setPhone(user.getPhone())
                .setBirthday(birthday)
                .setPostId(user.getPostId())
                .build();
    }

    public static User convert(UserServiceOuterClass.User user) {
        LocalDate birthday = DateConverter.convert(user.getBirthday());
        LocalDate registeredDate = DateConverter.convert(user.getRegisteredDate());
        Set<Role> roleSet = user.getRolesList().stream()
                .map(e -> new Role(e.getId(), e.getName()))
                .collect(Collectors.toSet());

        return User.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(roleSet)
                .enabled(user.getEnabled())
                .name(user.getName())
                .phone(user.getPhone())
                .birthday(birthday)
                .avatar(user.getAvatar())
                .city(user.getCity())
                .about(user.getAbout())
                .registeredDate(registeredDate)
                .post(PostConverter.convert(user.getPost()))
                .build();
    }
}
