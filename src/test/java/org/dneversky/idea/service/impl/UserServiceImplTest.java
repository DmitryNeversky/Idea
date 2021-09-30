package org.dneversky.idea.service.impl;

import lombok.var;
import org.dneversky.idea.entity.User;
import org.dneversky.idea.repository.NotificationRepository;
import org.dneversky.idea.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static reactor.core.publisher.Mono.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class UserServiceImplTest {

    @Mock
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
        userService.getAllUsers();

        verify(userRepository).findAll();
    }

    @Test
    void getUser() {
    }

    @Test
    void getUserByUsername() {
    }

    @Test
    void saveUser() {
        // given
        given(userRepository.getByUsername("e@e")).willReturn(new User("e@e", "123"));

        // when
        var user = userService.getUserByUsername("e@e");

        assertThat(user).isNotNull();
    }

    @Test
    void saveUserIfUserExists() {
        User user = new User();
    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUser() {
    }
}