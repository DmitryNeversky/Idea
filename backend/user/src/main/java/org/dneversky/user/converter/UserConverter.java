package org.dneversky.user.converter;

import com.google.protobuf.Timestamp;
import lombok.extern.slf4j.Slf4j;
import org.dneversky.gateway.UserServiceOuterClass;
import org.dneversky.user.dto.SaveUserRequest;
import org.dneversky.user.entity.User;
import org.dneversky.user.dto.UpdateUserRequest;

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
                .addAllRoles(roleSet)

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

    public static User convert(UserServiceOuterClass.SaveUserRequest user) {
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

    public static SaveUserRequest convertToSaveUserRequest(UserServiceOuterClass.SaveUserRequest user) {
        LocalDate birthday = DateConverter.convert(user.getBirthday());

        return SaveUserRequest.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .name(user.getName())
                .phone(user.getPhone())
                .birthday(birthday)
                .postId(user.getPostId())
                .build();
    }

    public static UpdateUserRequest convert(UserServiceOuterClass.UpdateUserRequest user) {
        LocalDate birthday = DateConverter.convert(user.getBirthday());

        return UpdateUserRequest.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .phone(user.getPhone())
                .birthday(birthday)
                .avatar(user.getAvatar())
                .city(user.getCity())
                .about(user.getAbout())
                .postId(user.getPostId())
                .build();
    }

    public static UserServiceOuterClass.UserPrincipal convertToUserPrincipal(User user) {
        Set<UserServiceOuterClass.Role> roleSet = user.getRoles().stream()
                .map(RoleConverter::convert)
                .collect(Collectors.toSet());

        return UserServiceOuterClass.UserPrincipal.newBuilder()
                .setId(user.getId())
                .setUsername(user.getUsername())
                .setPassword(user.getPassword())
                .addAllRoles(roleSet)
                .build();
    }
}
