package org.dneversky.gateway.service;

import org.dneversky.gateway.model.User;
import org.dneversky.gateway.security.UserPrincipal;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserByUsername(String username);
    UserPrincipal getUserPrincipalByUsername(String username);
    User getUserById(Long id);
}
