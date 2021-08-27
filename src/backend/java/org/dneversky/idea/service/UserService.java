package org.dneversky.idea.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dneversky.idea.entity.Role;
import org.dneversky.idea.entity.User;
import org.dneversky.idea.repository.RoleRepository;
import org.dneversky.idea.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Try to load user by username {}", username);
        User user = userRepository.findByUsername(username);
        if(user == null) {
            log.error("User with username {} not found in the database", username);
            throw new UsernameNotFoundException("User with username {} not found in the database");
        }
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    public List<User> getUsers() {
        log.info("Getting all users");
        return userRepository.findAll();
    }

    public User getUserByUsername(String username) {
        log.info("Getting user by username {}", username);
        return userRepository.findByUsername(username);
    }

    public User saveUser(User user) {
        log.info("Saving user {} to database", user.getUsername());
        if(userRepository.findByUsername(user.getUsername()) != null) {
            log.warn("User with username {} already contains in the database", user.getUsername());
            return null;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRegisteredDate(LocalDate.now());
        return userRepository.save(user);
    }

    public void deleteUser(User user) {
        // delete avatar
        log.info("Deleting user {}", user.getUsername());
        userRepository.delete(user);
    }

    public List<Role> getRoles() {
        log.info("Getting all roles");
        return roleRepository.findAll();
    }

    public Role getRoleByName(String roleName) {
        log.info("Getting role by name {}", roleName);
        return roleRepository.findByName(roleName);
    }

    public Role saveRole(Role role) {
        log.info("Saving role {} to database", role.getName());
        if(roleRepository.findByName(role.getName()) != null) {
            log.warn("Role {} already contains in the database", role.getName());
            return null;
        }
        return roleRepository.save(role);
    }

    public void deleteRole(Role role) {
        log.info("Deleting role {}", role.getName());
        userRepository.findAll().forEach(u -> {
            if(u.getRoles().contains(role)) {
                u.getRoles().remove(role);
                userRepository.save(u);
            }
        });
        roleRepository.delete(role);
    }

    public void addRoleToUser(String username, String roleName) {
        log.info("Add role {} to user {}", roleName, username);
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);
        if(user.getRoles().contains(role)) {
            log.warn("User {} already has role {}", user.getUsername(), role.getName());
            return;
        }
        user.getRoles().add(role);
    }
}
