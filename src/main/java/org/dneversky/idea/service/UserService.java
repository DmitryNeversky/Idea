package org.dneversky.idea.service;

import org.dneversky.idea.entity.Role;
import org.dneversky.idea.entity.User;
import org.dneversky.idea.payload.PasswordChangeRequest;
import org.dneversky.idea.payload.UserRequest;
import org.dneversky.idea.security.UserPrincipal;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface UserService {

    List<User> getAllUsers();

    User getUser(Long id);

    User getUserByUsername(String username);

    User saveUser(User user);

    User updateUser(String username, UserPrincipal principal, UserRequest userRequest, MultipartFile avatar, boolean removeAvatar);

    void deleteUser(String username, UserPrincipal userPrincipal);

    void patchPassword(String username, UserPrincipal userPrincipal, PasswordChangeRequest passwordChangeRequest);

    void deleteNotificationById(int id, User user);

    void blockUser(String username);

    void unblockUser(String username);

    void changeRoles(String username, Set<Role> roles);
}
