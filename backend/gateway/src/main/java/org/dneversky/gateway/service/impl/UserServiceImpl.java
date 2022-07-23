package org.dneversky.gateway.service.impl;

import org.dneversky.gateway.api.grpc.GRPCUserClient;
import org.dneversky.gateway.converter.UserConverter;
import org.dneversky.gateway.model.SaveUserRequest;
import org.dneversky.gateway.model.User;
import org.dneversky.gateway.security.UserPrincipal;
import org.dneversky.gateway.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    // TODO: Caching

    private final GRPCUserClient userClient;

    public UserServiceImpl(GRPCUserClient userClient) {
        this.userClient = userClient;
    }

    @Override
    public List<User> getAllUsers() {
        return userClient.getAllUsers().stream()
                .map(UserConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    public User getUserByUsername(String username) {
        return UserConverter.convert(userClient.getUserByUsername(username));
    }

    @Override
    public UserPrincipal getUserPrincipalByUsername(String username) {
        return UserPrincipal.buildPrincipal(userClient.getUserByUsername(username));
    }

    @Override
    public User getUserById(Long id) {
        return UserConverter.convert(userClient.getUserById(id));
    }

    @Override
    public User saveUser(SaveUserRequest userRequest) {
        return UserConverter.convert(userClient.saveUser(userRequest));
    }
}
