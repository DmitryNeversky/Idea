package org.dneversky.idea.service;

import org.dneversky.idea.entity.User;
import org.dneversky.idea.payload.PasswordChangeRequest;
import org.dneversky.idea.payload.UserRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getUser(Long id);

    User getUserByUsername(String username);

    User saveUser(User user);

    User updateUser(Long id, UserRequest userRequest, MultipartFile avatar, boolean removeAvatar);

    void deleteUser(Long id);

    void patchPassword(String username, PasswordChangeRequest passwordChangeRequest);
}
