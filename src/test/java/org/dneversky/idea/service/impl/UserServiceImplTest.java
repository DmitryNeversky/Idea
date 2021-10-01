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
    @Disabled
    void getUser() {
    }

    @Test
    void getUserByUsername() {
        // given
        given(userRepository.findByUsername(USER.getUsername())).willReturn(Optional.of(USER));

        // when
        userService.getUserByUsername(USER.getUsername());

        // then
        then(userRepository).should().findByUsername(USER.getUsername());
    }

    @Test
    void saveUser() {
        // given
        given(userRepository.save(USER)).willReturn(USER);

        // when
        userService.saveUser(USER);

        // then
        then(userRepository).should().save(USER);
    }

    @Test
    @Disabled
    void saveExistsUser() {
        given(userRepository.findByUsername(USER.getUsername())).willReturn(Optional.of(USER));
        willThrow(new EntityExistsException()).given(userRepository).save(USER);

        try {
            userService.saveUser(USER);
            fail("Should throw exception");
        } catch (EntityExistsException ignored) {}

        then(userRepository).should(never()).save(USER);
    }

    @Test
    @Disabled
    void updateUser() {
    }

    @Test
    @Disabled
    void deleteUser() {
    }
}