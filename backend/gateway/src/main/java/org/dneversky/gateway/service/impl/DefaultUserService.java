package org.dneversky.gateway.service.impl;

import org.dneversky.gateway.api.client.GRPCUserClient;
import org.dneversky.gateway.dto.SaveUserRequest;
import org.dneversky.gateway.dto.UpdateUserRequest;
import org.dneversky.gateway.dto.UserResponse;
import org.dneversky.gateway.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class DefaultUserService implements UserService {

    // TODO: Caching

    private final GRPCUserClient userClient;

    public DefaultUserService(GRPCUserClient userClient) {
        this.userClient = userClient;
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userClient.getAllUsers();
    }

    @Override
    public UserResponse getUserByUsername(String username) {
        return userClient.getUserByUsername(username);
    }

    @Override
    public UserResponse getUserById(Long id) {
        return userClient.getUserById(id);
    }

    @Override
    public UserResponse saveUser(SaveUserRequest userRequest) {
        return userClient.saveUser(userRequest);
    }

    @Override
    public UserResponse updateUser(String username, UpdateUserRequest userRequest, MultipartFile avatar, boolean removeAvatar) {
        return userClient.updateUser(userRequest);
    }

    @Override
    public void deleteUser(String username) {
        userClient.deleteUser(username);
    }
}
