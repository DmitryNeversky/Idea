package org.dneversky.user.converter;

import org.dneversky.gateway.UserServiceOuterClass;
import org.dneversky.user.entity.User;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserConverter {

    public static UserServiceOuterClass.User convert(User user) {
        return UserServiceOuterClass.User.newBuilder()
                .setId(user.getId())
                .setUsername(user.getUsername())
                .setPassword(user.getPassword())
                .addAllRoles(user.getRoles().stream()
                        .map(RoleConverter::convert)
                        .collect(Collectors.toList()))
                .setEnabled(user.isEnabled())
                .setName(Optional.ofNullable(user.getName()).orElse(""))
                .setPost(PostConverter.convert(user.getPost()))
                .setAbout(Optional.ofNullable(user.getAbout()).orElse(""))
                .setPhone(Optional.ofNullable(user.getPhone()).orElse(""))
                .setAvatar(Optional.ofNullable(user.getAvatar()).orElse(""))
                .setBirthday(Optional.ofNullable(user.getBirthday().toString()).orElse(""))
                .setRegisteredDate(Optional.of(user.getRegisteredDate().toString()).orElse(""))
                .build();
    }

    public static User convert(UserServiceOuterClass.UserToSave user) {
        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(new HashSet<>())
                .name(user.getName())
                .phone(user.getPhone())
                .birthday(new Date())
                .post(PostConverter.convert(user.getPost()))
                .build();
    }
}
