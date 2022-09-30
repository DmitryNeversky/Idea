package org.dneversky.idea.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dneversky.idea.entity.Role;
import org.dneversky.idea.entity.User;
import org.dneversky.idea.exception.PermissionException;
import org.dneversky.idea.payload.PasswordChangeRequest;
import org.dneversky.idea.payload.UserRequest;
import org.dneversky.idea.repository.UserRepository;
import org.dneversky.idea.security.UserPrincipal;
import org.dneversky.idea.service.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleServiceImpl roleServiceImpl;
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
        if(userRepository.findByUsername(user.getUsername()).isPresent())
            throw new EntityExistsException("User with username " + user.getUsername() + " already exists.");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRegisteredDate(LocalDate.now());
        if(admin) user.getRoles().add(roleServiceImpl.getRole(3));
        user.getRoles().add(roleServiceImpl.getRole("USER"));
        user.setEnabled(true);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(String username, UserPrincipal principal, UserRequest userRequest, MultipartFile avatar, boolean removeAvatar) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException("User with username " + username + " not found in the database."));
        if(principal.getUsername().equals(username) || principal.isAdmin()) {
            if (removeAvatar) {
                imageService.removeImage(user.getAvatar());
                user.setAvatar(null);
            }
            if (avatar != null && !Objects.requireNonNull(avatar.getOriginalFilename()).isEmpty()) {
                user.setAvatar(imageService.saveImage(avatar));
            }
            user.setName(userRequest.getName());
            user.setPhone(userRequest.getPhone());
            user.setBirthday(userRequest.getBirthday());
            user.setCity(userRequest.getCity());
            user.setAbout(userRequest.getAbout());
            user.setPost(userRequest.getPost());
            return userRepository.save(user);
        }
        throw new PermissionException("You don't have permission to update a profile of user with username " + username + ".");
    }

    @Override
    public void deleteUser(String username, UserPrincipal userPrincipal) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException("User with username " + username + " not found in the database."));
        if(userPrincipal.getUsername().equals(username) || userPrincipal.isAdmin()) {
            imageService.removeImage(user.getAvatar());
            userRepository.delete(user);
            log.info("User {} deleted.", user.getUsername());
            return;
        }

        throw new PermissionException("You don't have permission to delete user with username " + username + ".");
    }

    public boolean verifyOldPassword(String username, String oldPassword) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException("User with username " + username + " not found in the database."));
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

    public boolean verifyNewPassword(String username, String newPassword) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException("User with username " + username + " not found in the database."));
        return passwordEncoder.matches(newPassword, user.getPassword());
    }

    @Override
    public void patchPassword(String username, UserPrincipal userPrincipal, PasswordChangeRequest passwordChangeRequest) {
        if(userPrincipal.getUsername().equals(username) || userPrincipal.isAdmin()) {
            User user = userRepository.findByUsername(username).orElseThrow(
                    () -> new EntityNotFoundException("User with username " + username + " not found in the database."));
            user.setPassword(passwordEncoder.encode(passwordChangeRequest.getNewPassword()));
            userRepository.save(user);
            return;
        }
        throw new PermissionException("You don't have permission to change the password of user with username " + username + ".");
    }

    @Override
    public void blockUser(String username, UserPrincipal principal) {
        if(principal.isAdmin()) {
            User user = userRepository.findByUsername(username).orElseThrow(
                    () -> new EntityNotFoundException("User with username " + username + " not found in the database."));
            user.setEnabled(false);
            userRepository.save(user);
            return;
        }
        throw new PermissionException("You don't have permission to block user with username " + username + ".");
    }

    @Override
    public void unblockUser(String username, UserPrincipal principal) {
        if(principal.isAdmin()) {
            User user = userRepository.findByUsername(username).orElseThrow(
                    () -> new EntityNotFoundException("User with username " + username + " not found in the database."));
            user.setEnabled(true);
            userRepository.save(user);
            return;
        }
        throw new PermissionException("You don't have permission to unblock user with username " + username + ".");
    }

    @Override
    public void changeRoles(String username, Set<Role> roles, UserPrincipal principal) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException("User with username " + username + " not found in the database."));
        if(principal.isSuperAdmin()) {
            user.setRoles(roles);
            userRepository.save(user);
            return;
        }
        throw new PermissionException("You don't have permission to change roles for user with username " + username + ".");
    }
}
