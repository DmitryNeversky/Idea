package org.dneversky.gateway.api.client;

import org.dneversky.gateway.UserServiceOuterClass;
import org.dneversky.gateway.dto.SaveUserRequest;
import org.dneversky.gateway.dto.UpdateUserRequest;
import org.dneversky.gateway.security.UserPrincipal;

import java.util.List;

public interface UserClient {

    List<UserServiceOuterClass.User> getAllUsers();

    UserServiceOuterClass.User getUserByUsername(String username);

    UserPrincipal getUserPrincipalByUsername(String username);

    UserServiceOuterClass.User getUserById(Long id);

    UserServiceOuterClass.User saveUser(SaveUserRequest userRequest);

    UserServiceOuterClass.User updateUser(UpdateUserRequest userRequest);
}
