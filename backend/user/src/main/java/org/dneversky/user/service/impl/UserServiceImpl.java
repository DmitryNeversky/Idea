package org.dneversky.user.service.impl;

import org.dneversky.user.entity.Role;
import org.dneversky.user.entity.User;
import org.dneversky.user.exception.EntityNotFoundException;
import org.dneversky.user.model.PasswordChangeRequest;
import org.dneversky.user.model.UserRequest;
import org.dneversky.user.repository.UserRepository;
import org.dneversky.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    @Override
    public User getUser(Long id) {

        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User with id " + id + " not found in the database."));
    }

    @Override
    public User getUserByUsername(String username) {

        return userRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException("User with username " + username + " not found in the database."));
    }

    @Override
    public User saveUser(User user) {
        return null;
    }

    @Override
    public User updateUser(String username, UserRequest userRequest, MultipartFile avatar, boolean removeAvatar) {
        return null;
    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void patchPassword(String username, PasswordChangeRequest passwordChangeRequest) {

    }

    @Override
    public void blockUser(String username) {

    }

    @Override
    public void unblockUser(String username) {

    }

    @Override
    public void changeRoles(String username, Set<Role> roles) {

    }
}
