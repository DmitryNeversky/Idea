package org.dneversky.user.converter;

import org.dneversky.gateway.UserServiceOuterClass;
import org.dneversky.user.entity.Role;

public class RoleConverter {

    public static UserServiceOuterClass.Role convert(Role role) {
        return UserServiceOuterClass.Role.newBuilder()
                .setId(role.getId())
                .setName(role.getName())
                .build();
    }
}
