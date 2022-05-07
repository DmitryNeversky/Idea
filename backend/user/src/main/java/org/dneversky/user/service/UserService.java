package org.dneversky.user.service;

import com.sun.security.auth.UserPrincipal;
import org.dneversky.user.entity.Role;
import org.dneversky.user.entity.User;
import org.dneversky.user.model.PasswordChangeRequest;
import org.dneversky.user.model.UserRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface UserService {

    List<User> getAllUsers();

    User getUser(Long id);

    User getUserByUsername(String username);

    User saveUser(User user);

    User updateUser(String username, UserRequest userRequest, MultipartFile avatar, boolean removeAvatar);

    void deleteUser(String username);

    void patchPassword(String username, PasswordChangeRequest passwordChangeRequest);

    void blockUser(String username);

    void unblockUser(String username);

    void changeRoles(String username, Set<Role> roles);
}
