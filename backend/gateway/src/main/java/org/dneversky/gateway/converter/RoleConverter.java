package org.dneversky.gateway.converter;

import org.dneversky.gateway.UserServiceOuterClass;
import org.dneversky.gateway.model.Role;

public final class RoleConverter {

    public static UserServiceOuterClass.Role convert(Role role) {
        return UserServiceOuterClass.Role.newBuilder()
                .setId(role.getId())
                .setName(role.getName())
                .build();
    }

    public static Role convert(UserServiceOuterClass.Role role) {
        return Role.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
    }
}
