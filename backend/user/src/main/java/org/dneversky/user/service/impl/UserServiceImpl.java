package org.dneversky.user.service.impl;

import org.dneversky.user.entity.Role;
import org.dneversky.user.entity.User;
import org.dneversky.user.exception.EntityExistsException;
import org.dneversky.user.exception.EntityNotFoundException;
import org.dneversky.user.model.PasswordChangeRequest;
import org.dneversky.user.model.UserRequest;
import org.dneversky.user.repository.UserRepository;
import org.dneversky.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleServiceImpl roleService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleServiceImpl roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
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
    public User saveUser(User user) {
        if(userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new EntityExistsException("User with username " + user.getUsername() + " already exists.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRegisteredDate(LocalDate.now());
        user.getRoles().add(roleService.getRole("USER"));
        user.setEnabled(true);

        return userRepository.save(user);
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
