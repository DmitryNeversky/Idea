package org.dneversky.user.converter;

import org.dneversky.gateway.UserServiceOuterClass;
import org.dneversky.user.entity.User;

public class UserGRPCConverter {

    public static UserServiceOuterClass.User convert(User user) {
//        UserServiceOuterClass.Role roles = user.getRoles().stream()
//                .map(e -> UserServiceOuterClass.Role.newBuilder()
//                        .setId(e.getId())
//                        .setName(e.getName())
//                        .build())
//                .collect(Collectors.toList());
        return UserServiceOuterClass.User.newBuilder()
                .setId(user.getId())
                .setUsername(user.getUsername())
                .setPassword(user.getPassword())
//                .setRoles(roles)
                .setEnabled(user.isEnabled())
                .setName(user.getName())
//                .setPost(user.getPost())
//                .setAbout(user.getAbout())
                .setPhone(user.getPhone())
//                .setAvatar(user.getAvatar())
                .setBirthday(user.getBirthday().toString())
                .setRegisteredDate(user.getRegisteredDate().toString())
                .build();
    }
}
