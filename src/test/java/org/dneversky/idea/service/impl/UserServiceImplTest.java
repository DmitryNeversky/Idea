package org.dneversky.idea.service.impl;

import org.dneversky.idea.entity.User;
import org.dneversky.idea.exception.EntityExistsException;
import org.dneversky.idea.exception.EntityNotFoundException;
import org.dneversky.idea.payload.UserRequest;
import org.dneversky.idea.repository.NotificationRepository;
import org.dneversky.idea.repository.UserRepository;
import org.dneversky.idea.security.UserPrincipal;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class UserServiceImplTest {

    private final User USER = new User("e@e", "123");
    private final UserRequest USER_REQUEST = new UserRequest("Petr Ivanov Alekseev", "9999999999", new Date());
    private final UserPrincipal userPrincipal = new UserPrincipal(null, "e@e", "123", null, true);

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
        given(userRepository.findByUsername(USER.getUsername())).willReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUserByUsername(USER.getUsername()))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("User with username " + USER.getUsername() + " not found in the database.");

        verify(userRepository, atLeastOnce()).findByUsername(USER.getUsername());
    }

//    @Test
//    void getExistedUserById() {
//        given(userRepository.findById(1L)).willReturn(Optional.of(USER));
//
//        userService.getUser(1L);
//
//        then(userRepository).should().findById(1L);
//    }

    @Test
    void getNotExistedUserById() {
        given(userRepository.findById(USER.getId())).willReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUser(USER.getId()))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("User with id " + USER.getId() + " not found in the database.");

        verify(userRepository, atLeastOnce()).findById(USER.getId());
    }

    @Test
    void saveNotExistedUser() {
        given(userRepository.save(USER)).willReturn(USER);

        userService.saveUser(USER);

        then(userRepository).should().save(USER);
    }

    @Test
    void saveExistedUser() {
        given(userRepository.findByUsername(USER.getUsername())).willReturn(Optional.of(USER));

        assertThatThrownBy(() -> userService.saveUser(USER))
                .isInstanceOf(EntityExistsException.class)
                .hasMessageContaining("User with username " + USER.getUsername() + " already exists.");

        verify(userRepository, never()).save(any());
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
    void deleteExistedUser() {
        given(userRepository.findByUsername(USER.getUsername())).willReturn(Optional.of(USER));

        userService.deleteUser(USER.getUsername(), userPrincipal);

        verify(userRepository).delete(USER);
    }

    @Test
    void deleteUnExistedIfCurrentUser() {
        assertThatThrownBy(() -> userService.deleteUser(USER.getUsername(), any()))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("User with username " + USER.getUsername() + " not found in the database.");

        verify(userRepository, never()).delete(USER);
    }

    @Test
    @Disabled
    void deleteUnExistedIfAdmin() {
        assertThatThrownBy(() -> userService.deleteUser(USER.getUsername(), userPrincipal))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("User with username " + USER.getUsername() + " not found in the database.");

        verify(userRepository, never()).delete(USER);
    }
}