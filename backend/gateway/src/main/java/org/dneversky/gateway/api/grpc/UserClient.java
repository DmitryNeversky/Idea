package org.dneversky.gateway.api.grpc;

import org.dneversky.gateway.UserServiceOuterClass;
import org.dneversky.gateway.model.SaveUserRequest;

import java.util.List;

public interface UserClient {
    List<UserServiceOuterClass.User> getAllUsers();
    UserServiceOuterClass.User getUserByUsername(String username);
    UserServiceOuterClass.User getUserById(Long id);
    UserServiceOuterClass.User saveUser(SaveUserRequest userRequest);
}
