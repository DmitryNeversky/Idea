package org.dneversky.user.service;

import org.dneversky.user.dto.SaveUserRequest;
import org.dneversky.user.entity.Role;
import org.dneversky.user.entity.User;
import org.dneversky.user.dto.PasswordChangeRequest;
import org.dneversky.user.dto.UpdateUserRequest;

import java.util.List;
import java.util.Set;

public interface UserService {

    List<User> getAllUsers();

    User getUserById(Long id);

    User getUserByUsername(String username);

    User saveUser(SaveUserRequest user);

    User updateUser(String username, UpdateUserRequest userRequest);

    void deleteUser(String username);

    void patchPassword(String username, PasswordChangeRequest passwordChangeRequest);

    void blockUser(String username);

    void unblockUser(String username);

    void changeRoles(String username, Set<Role> roles);
}
