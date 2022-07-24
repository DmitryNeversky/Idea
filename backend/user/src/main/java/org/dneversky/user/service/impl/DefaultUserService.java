package org.dneversky.user.service.impl;

import org.dneversky.user.dto.PasswordChangeRequest;
import org.dneversky.user.dto.SaveUserRequest;
import org.dneversky.user.dto.UpdateUserRequest;
import org.dneversky.user.entity.Post;
import org.dneversky.user.entity.Role;
import org.dneversky.user.entity.User;
import org.dneversky.user.exception.EntityExistsException;
import org.dneversky.user.exception.EntityNotFoundException;
import org.dneversky.user.repository.UserRepository;
import org.dneversky.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final DefaultRoleService roleService;
    private final DefaultPostService postService;

    @Autowired
    public DefaultUserService(UserRepository userRepository, PasswordEncoder passwordEncoder, DefaultRoleService roleService, DefaultPostService postService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.postService = postService;
    }

    @Override
    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {

        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User with id " + id + " not found in the database."));
    }

    @Override
    public User getUserByUsername(String username) {

        return userRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException("User with username " + username + " not found in the database."));
    }

    @Override
    public User saveUser(SaveUserRequest userRequest) {
        if(userRepository.findByUsername(userRequest.getUsername()).isPresent()) {
            throw new EntityExistsException("User with username " + userRequest.getUsername() + " already exists.");
        }

        Post post = postService.getPost(userRequest.getPostId());
        User user = new User(userRequest);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setRegisteredDate(LocalDate.now());
        user.setPost(post);
        user.getRoles().add(roleService.getRole("USER"));
        user.setEnabled(true);

        return userRepository.save(user);
    }

    @Override
    public User updateUser(String username, UpdateUserRequest userRequest) {
        if(!userRepository.findByUsername(userRequest.getUsername()).isPresent()) {
            throw new EntityNotFoundException("User with username " + userRequest.getUsername() + " not found.");
        }

        Post post = postService.getPost(userRequest.getPostId());
        User user = userRepository.getByUsername(userRequest.getUsername());
        user.setPost(post);
        user.mergeUpdateRequest(userRequest);

        return user;
    }

    @Override
    public void deleteUser(String username) {
        if(!userRepository.findByUsername(username).isPresent()) {
            throw new EntityNotFoundException("User with username " + username + " not found.");
        }

        User user = userRepository.getByUsername(username);
        userRepository.delete(user);
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
