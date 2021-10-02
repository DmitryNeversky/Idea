package org.dneversky.idea.service.impl;

import org.dneversky.idea.entity.User;
import org.dneversky.idea.repository.NotificationRepository;
import org.dneversky.idea.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class UserServiceImplTest {

    private final User USER = new User("e@e", "123");

    @Spy
    private UserRepository userRepository;
    @Mock
    private NotificationRepository notificationRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private RoleServiceImpl roleServiceImpl;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void getAllUsers() {
        given(userRepository.findAll()).willReturn(new ArrayList<>());

        userService.getAllUsers();

        then(userRepository).should().findAll();
    }

    @Test
    void getExistedUserByUsername() {
        given(userRepository.findByUsername(USER.getUsername())).willReturn(Optional.of(USER));

        userService.getUserByUsername(USER.getUsername());

        then(userRepository).should().findByUsername(USER.getUsername());
    }

    @Test
    void getNotExistedUserByUsername() {
        willThrow(new EntityNotFoundException()).given(userRepository).findByUsername(anyString());

        try {
            userService.getUserByUsername(anyString());
            fail("Should be throwing...");
        } catch (EntityNotFoundException ignored) { }

        then(userRepository).should().findByUsername(anyString());
    }

    @Test
    void getExistedUserById() {
        given(userRepository.findById(1L)).willReturn(Optional.of(USER));

        userService.getUser(1L);

        then(userRepository).should().findById(1L);
    }

    @Test
    void getNotExistedUserById() {
        willThrow(new EntityNotFoundException()).given(userRepository).findById(anyLong());

        try {
            userService.getUser(anyLong());
            fail("Should be throwing...");
        } catch (EntityNotFoundException ignored) { }

        then(userRepository).should().findById(anyLong());
    }

    @Test
    void saveNotExistedUser() {
        given(userRepository.save(USER)).willReturn(USER);

        userService.saveUser(USER);

        then(userRepository).should().save(USER);
    }

    @Test
    void saveExistedUser() {
        willThrow(new EntityExistsException()).given(userRepository).save(USER);

        try {
            userService.saveUser(USER);
            fail("Should be throwing...");
        } catch (EntityExistsException ignored) { }

        then(userRepository).should().save(USER);
    }

    @Test
    @Disabled
    void updateExistedUser() {

    }

    @Test
    @Disabled
    void updateNotExistedUser() {
    }

    @Test
    @Disabled
    void deleteExistedUser() {

        then(userRepository).should().delete(USER);
    }
}