package org.dneversky.gateway.service.impl;

import org.dneversky.gateway.api.client.GRPCUserClient;
import org.dneversky.gateway.converter.UserConverter;
import org.dneversky.gateway.dto.SaveUserRequest;
import org.dneversky.gateway.dto.UpdateUserRequest;
import org.dneversky.gateway.dto.UserResponse;
import org.dneversky.gateway.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultUserService implements UserService {

    // TODO: Caching

    private final GRPCUserClient userClient;

    public DefaultUserService(GRPCUserClient userClient) {
        this.userClient = userClient;
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userClient.getAllUsers().stream()
                .map(UserConverter::convertToResponseUser)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse getUserByUsername(String username) {
        return UserConverter.convertToResponseUser(userClient.getUserByUsername(username));
    }

    @Override
    public UserResponse getUserById(Long id) {
        return UserConverter.convertToResponseUser(userClient.getUserById(id));
    }

    @Override
    public UserResponse saveUser(SaveUserRequest userRequest) {
        return UserConverter.convertToResponseUser(userClient.saveUser(userRequest));
    }

    @Override
    public UserResponse updateUser(String username, UpdateUserRequest userRequest, MultipartFile avatar, boolean removeAvatar) {
        return UserConverter.convertToResponseUser(userClient.updateUser(userRequest));
    }
}
