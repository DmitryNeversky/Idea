package org.dneversky.idea.service.impl;

import org.dneversky.idea.entity.User;
import org.dneversky.idea.payload.PasswordChangeRequest;
import org.dneversky.idea.payload.UserRequest;
import org.dneversky.idea.repository.IdeaRepository;
import org.dneversky.idea.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class UserServiceFacade implements UserService {

    private final IdeaRepository ideaRepository;
    private final UserServiceImpl userService;

    @Autowired
    public UserServiceFacade(IdeaRepository ideaRepository, UserServiceImpl userService) {
        this.ideaRepository = ideaRepository;
        this.userService = userService;
    }

    @Override
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @Override
    public User getUser(long id) {
        return userService.getUser(id);
    }

    @Override
    public User getUser(String username) {
        return userService.getUser(username);
    }

    @Override
    public User createUser(User user, boolean admin) {
        return userService.createUser(user, admin);
    }

    @Override
    public User updateUser(String username, UserRequest userRequest, MultipartFile avatar, boolean removeAvatar) {
        return userService.updateUser(username, userRequest, avatar, removeAvatar);
    }

    @Override
    public void deleteUser(String username) {
        userService.deleteUser(username);
    }

    @Override
    public boolean verifyOldPassword(String username, String oldPassword) {
        return userService.verifyOldPassword(username, oldPassword);
    }

    @Override
    public boolean verifyNewPassword(String username, String newPassword) {
        return userService.verifyNewPassword(username, newPassword);
    }

    @Override
    public void patchPassword(String username, PasswordChangeRequest passwordChangeRequest) {
        userService.patchPassword(username, passwordChangeRequest);
    }

    @Override
    public void blockUser(String username) {
        userService.blockUser(username);
    }

    @Override
    public void unblockUser(String username) {
        userService.unblockUser(username);
    }

    @Override
    public void changeRoles(String username, String role) {
        userService.changeRoles(username, role);
    }

}
