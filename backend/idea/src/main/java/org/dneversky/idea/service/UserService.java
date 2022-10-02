package org.dneversky.idea.service;

import org.dneversky.idea.entity.User;
import org.dneversky.idea.payload.PasswordChangeRequest;
import org.dneversky.idea.payload.UserRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getUser(long id);

    User getUser(String username);

    User createUser(User user, boolean admin);

    User updateUser(String username, UserRequest userRequest, MultipartFile avatar, boolean removeAvatar);

    void deleteUser(String username);

    void patchPassword(String username, PasswordChangeRequest passwordChangeRequest);

    void blockUser(String username);

    void unblockUser(String username);

    void changeRoles(String username, String role);

    boolean verifyOldPassword(String username, String currentPassword);

    boolean verifyNewPassword(String username, String newPassword);
}
