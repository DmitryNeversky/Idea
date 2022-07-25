package org.dneversky.gateway.converter;

import com.google.protobuf.Timestamp;
import org.dneversky.gateway.UserServiceOuterClass;
import org.dneversky.gateway.dto.SaveUserRequest;
import org.dneversky.gateway.dto.UpdateUserRequest;
import org.dneversky.gateway.dto.UserResponse;
import org.dneversky.gateway.model.Role;
import org.dneversky.gateway.security.UserPrincipal;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public final class UserConverter {

    public static UserServiceOuterClass.SaveUserRequest convertToOuterUser(SaveUserRequest user) {
        Timestamp birthday = DateConverter.convert(user.getBirthday());

        return UserServiceOuterClass.SaveUserRequest.newBuilder()
                .setUsername(user.getUsername())
                .setPassword(user.getPassword())
                .setName(user.getName())
                .setPhone(user.getPhone())
                .setBirthday(birthday)
                .setPostId(user.getPostId())
                .build();
    }

    public static UserResponse convertToUserResponse(UserServiceOuterClass.User user) {
        LocalDate birthday = DateConverter.convert(user.getBirthday());
        LocalDate registeredDate = DateConverter.convert(user.getRegisteredDate());
        Set<Role> roleSet = user.getRolesList().stream()
                .map(e -> new Role(e.getId(), e.getName()))
                .collect(Collectors.toSet());

        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .roles(roleSet)
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

    public static UserServiceOuterClass.UpdateUserRequest convertToOuterUser(UpdateUserRequest user) {
        Timestamp birthday = DateConverter.convert(user.getBirthday());

        return UserServiceOuterClass.UpdateUserRequest.newBuilder()
                .setId(user.getId())
                .setUsername(user.getUsername())
                .setName(user.getName())
                .setPhone(user.getPhone())
                .setBirthday(birthday)
                .setPostId(user.getPostId())
                .setAbout(Optional.ofNullable(user.getAbout()).orElse(""))
                .setAvatar(Optional.ofNullable(user.getAvatar()).orElse(""))
                .build();
    }

    // Is it safe?
    public static UserPrincipal convertToUserPrincipal(UserServiceOuterClass.UserPrincipal principal) {
        Set<GrantedAuthority> roleSet = principal.getRolesList().stream()
                .map(e -> new SimpleGrantedAuthority(e.getName()))
                .collect(Collectors.toSet());

        return new UserPrincipal(
                principal.getId(),
                principal.getUsername(),
                principal.getPassword(),
                roleSet,
                principal.getEnabled());
    }
}
