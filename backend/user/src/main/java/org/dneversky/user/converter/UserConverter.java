package org.dneversky.user.converter;

import com.google.protobuf.Timestamp;
import lombok.extern.slf4j.Slf4j;
import org.dneversky.gateway.UserServiceOuterClass;
import org.dneversky.user.entity.User;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public final class UserConverter {

    public static UserServiceOuterClass.User convert(User user) {

        Timestamp birthday = DateConverter.convert(user.getBirthday());
        Timestamp registeredDate = DateConverter.convert(user.getRegisteredDate());
        Set<UserServiceOuterClass.Role> roleSet = user.getRoles().stream()
                .map(RoleConverter::convert)
                .collect(Collectors.toSet());

        return UserServiceOuterClass.User.newBuilder()
                .setUsername(user.getUsername())
                .setPassword(user.getPassword())
                .addAllRoles(roleSet)
                .setEnabled(user.isEnabled())

                .setId(user.getId())
                .setName(user.getName())
                .setBirthday(birthday)
                .setRegisteredDate(registeredDate)
                .setPost(PostConverter.convert(user.getPost()))
                .setAbout(Optional.ofNullable(user.getAbout()).orElse(""))
                .setPhone(Optional.ofNullable(user.getPhone()).orElse(""))
                .setAvatar(Optional.ofNullable(user.getAvatar()).orElse(""))
                .build();
    }

    public static User convert(UserServiceOuterClass.UserToSave user) {
        LocalDate birthday = DateConverter.convert(user.getBirthday());

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(new HashSet<>())
                .name(user.getName())
                .phone(user.getPhone())
                .birthday(birthday)
                .build();
    }
}
