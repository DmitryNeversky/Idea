package org.dneversky.idea.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dneversky.idea.entity.Post;
import org.dneversky.idea.entity.User;
import org.dneversky.idea.repository.NotificationRepository;
import org.dneversky.idea.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService implements UserDetailsService {

    @Value("${uploadPath}")
    private String UPLOAD_PATH;

    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;
    private final PasswordEncoder passwordEncoder;
    private final PostService postService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null) {
            log.error("User with username {} not found in the database", username);
            throw new UsernameNotFoundException("User with username {} not found in the database");
        }
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    public List<User> getUsers() {

        return userRepository.findAll();
    }

    public User getUserById(int id) {

        return userRepository.findById(id).orElse(null);
    }

    public User getUserByUsername(String username) {

        return userRepository.findByUsername(username);
    }

    public User saveUser(User user) {
        if(userRepository.findByUsername(user.getUsername()) != null) {
            log.warn("User with username {} already contains in the database", user.getUsername());
            return null;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRegisteredDate(LocalDate.now());

        Post post = postService.getPostByName(user.getPost().getName());
        post.getUsers().add(user);

        user.setPost(post);

        return userRepository.save(user);
    }

    public User putUser(User user, MultipartFile avatar, boolean removeAvatar) {
        if(removeAvatar) {
            removeAvatar(user);
        } uploadAvatar(user, avatar);

        Post post = postService.getPostByName(user.getPost().getName());
        post.getUsers().add(user);

        user.setPost(post);

        return userRepository.save(user);
    }

    public void deleteUser(int id) {
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent())
            return;

        log.info("Deleting user {}", user.get().getUsername());
        removeAvatar(user.get());

        userRepository.delete(user.get());
    }

    public void deleteNotificationById(int id, User user) {
        user.getNotifications().remove(notificationRepository.findById(id));

        userRepository.save(user);
    }

    public boolean verifyOldPassword(String username, String oldPassword) {
        User user = userRepository.findByUsername(username);

        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

    public boolean verifyNewPassword(String username, String newPassword) {
        User user = userRepository.findByUsername(username);

        return passwordEncoder.matches(newPassword, user.getPassword());
    }

    public void changePassword(String username, String newPassword) {
        User user = userRepository.findByUsername(username);
        user.setPassword(passwordEncoder.encode(newPassword));

        userRepository.save(user);
    }

    private void uploadAvatar(User user, MultipartFile avatar) {
        if (avatar != null) {
            removeAvatar(user);
            String fileName = java.util.UUID.randomUUID() + "_"
                    + StringUtils.cleanPath(Objects.requireNonNull(avatar.getOriginalFilename()));
            try {
                Path path = Paths.get(UPLOAD_PATH + "images/avatar/" + fileName);
                Files.copy(avatar.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                user.setAvatar(fileName);
            } catch (IOException e) {
                log.error("Uploading user's avatar error: {}", e.getMessage());
            }
        }
    }

    private void removeAvatar(User user) {
        if (user.getAvatar() != null) {
            if (Files.exists(Paths.get(UPLOAD_PATH + "images/avatar/" + user.getAvatar()))) {
                try {
                    Files.delete(Paths.get(UPLOAD_PATH + "images/avatar/" + user.getAvatar()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            user.setAvatar(null);
        }
    }
}
