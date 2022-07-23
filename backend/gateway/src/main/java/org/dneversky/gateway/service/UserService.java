package org.dneversky.gateway.service;

import org.dneversky.gateway.dto.SaveUserRequest;
import org.dneversky.gateway.dto.UpdateUserRequest;
import org.dneversky.gateway.dto.UserResponse;
import org.dneversky.gateway.security.UserPrincipal;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    List<UserResponse> getAllUsers();
    UserResponse getUserByUsername(String username);
    UserResponse getUserById(Long id);
    UserResponse saveUser(SaveUserRequest userRequest);
    UserResponse updateUser(String username, UserPrincipal userPrincipal, UpdateUserRequest userRequest, MultipartFile avatar, boolean removeAvatar);
}
