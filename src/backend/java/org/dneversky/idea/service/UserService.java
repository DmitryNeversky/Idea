package org.dneversky.idea.service;

import org.dneversky.idea.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getUser(Long id);

    User getUserByUsername(String username);

    User saveUser(User user);

    User updateUser(Long id, User user, MultipartFile avatar, boolean removeAvatar);

    void deleteUser(Long id);
}
