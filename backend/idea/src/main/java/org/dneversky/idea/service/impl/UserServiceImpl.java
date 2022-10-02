package org.dneversky.idea.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dneversky.idea.entity.Role;
import org.dneversky.idea.entity.User;
import org.dneversky.idea.payload.PasswordChangeRequest;
import org.dneversky.idea.payload.UserRequest;
import org.dneversky.idea.repository.UserRepository;
import org.dneversky.idea.service.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ImageServiceImpl imageService;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User with id " + id + " not found in the database."));
    }

    @Override
    public User getUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User with username " + username + " not found in the database."));
    }

    @Override
    public User createUser(User user, boolean admin) {
        if(getUser(user.getUsername()) != null)
            throw new EntityExistsException("User with username " + user.getUsername() + " already exists.");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRegisteredDate(LocalDate.now());
        user.setRole(Role.USER);
        user.setEnabled(true);
        if(admin) user.setRole(Role.ADMIN);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(String username, UserRequest userRequest, MultipartFile avatar, boolean removeAvatar) {
        User user = getUser(username);
        if (removeAvatar) {
            imageService.removeImage(user.getAvatar());
            user.setAvatar(null);
        }
        if (avatar != null && !Objects.requireNonNull(avatar.getOriginalFilename()).isEmpty()) {
            user.setAvatar(imageService.saveImage(avatar));
        }
        user.buildUser(userRequest);
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(String username) {
        User user = getUser(username);
        imageService.removeImage(user.getAvatar());
        userRepository.delete(user);
        log.info("User with username {} deleted.", user.getUsername());
    }

    public boolean verifyOldPassword(String username, String oldPassword) {
        User user = getUser(username);
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

    public boolean verifyNewPassword(String username, String newPassword) {
        User user = getUser(username);
        return passwordEncoder.matches(newPassword, user.getPassword());
    }

    @Override
    public void patchPassword(String username, PasswordChangeRequest passwordChangeRequest) {
        User user = getUser(username);
        user.setPassword(passwordEncoder.encode(passwordChangeRequest.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public void blockUser(String username) {
        User user = getUser(username);
        user.setEnabled(false);
        userRepository.save(user);
    }

    @Override
    public void unblockUser(String username) {
        User user = getUser(username);
        user.setEnabled(true);
        userRepository.save(user);
    }

    @Override
    public void changeRoles(String username, String role) {
        User user = getUser(username);
        user.setRole(Role.valueOf(Role.class, role));
        userRepository.save(user);
    }
}
