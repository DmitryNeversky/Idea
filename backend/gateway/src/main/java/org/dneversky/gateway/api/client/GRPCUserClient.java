package org.dneversky.gateway.api.client;

import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.dneversky.gateway.UserServiceGrpc;
import org.dneversky.gateway.UserServiceOuterClass;
import org.dneversky.gateway.converter.UserConverter;
import org.dneversky.gateway.dto.SaveUserRequest;
import org.dneversky.gateway.dto.UpdateUserRequest;
import org.dneversky.gateway.dto.UserResponse;
import org.dneversky.gateway.security.UserPrincipal;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class GRPCUserClient {

    @GrpcClient(value = "localhost:9090")
    private UserServiceGrpc.UserServiceBlockingStub stub;

    public List<UserResponse> getAllUsers() {
        log.info("Getting all users via grpc...");

        UserServiceOuterClass.AllUsersRequest request = UserServiceOuterClass.AllUsersRequest.newBuilder().build();
        UserServiceOuterClass.AllUsersResponse response = stub.getAllUsers(request);

        log.info("Received users from grpc in count {}.", response.getUsersCount());

        return response.getUsersList().stream()
                .map(UserConverter::convertToUserResponse)
                .collect(Collectors.toList());
    }

    public UserResponse getUserByUsername(String username) {
        log.info("Getting user by username {} via grpc...", username);

        UserServiceOuterClass.UserByUsernameRequest request = UserServiceOuterClass.UserByUsernameRequest
                .newBuilder().setUsername(username).build();
        UserServiceOuterClass.User response = stub.getUserByUsername(request);

        log.info("Received user with username {} from grpc.", username);

        return UserConverter.convertToUserResponse(response);
    }

    public UserPrincipal getUserPrincipalByUsername(String username) {
        log.info("Getting principal by username {} via grpc...", username);

        UserServiceOuterClass.UserByUsernameRequest request = UserServiceOuterClass.UserByUsernameRequest
                .newBuilder().setUsername(username).build();
        UserServiceOuterClass.UserPrincipal response = stub.getUserPrincipalByUsername(request);

        log.info("Received principal with username {} from grpc.", username);

        return UserConverter.convertToUserPrincipal(response);
    }

    public UserResponse getUserById(Long id) {
        log.info("Getting user by id {} via grpc...", id);

        UserServiceOuterClass.UserByIdRequest request = UserServiceOuterClass.UserByIdRequest
                .newBuilder().setId(id).build();
        UserServiceOuterClass.User response = stub.getUserById(request);

        log.info("Received user with id {} from grpc.", id);

        return UserConverter.convertToUserResponse(response);
    }

    public UserResponse saveUser(SaveUserRequest userRequest) {
        log.info("Saving user with username {} via grpc...", userRequest.getUsername());

        UserServiceOuterClass.SaveUserRequest request = UserConverter.convertToOuterUser(userRequest);
        UserServiceOuterClass.User response = stub.saveUser(request);

        log.info("Received user with id {} and username {} from grpc.", response.getId(), userRequest.getUsername());

        return UserConverter.convertToUserResponse(response);
    }

    public UserResponse updateUser(UpdateUserRequest userRequest) {
        log.info("Updating user with username {} via grpc...", userRequest.getUsername());

        UserServiceOuterClass.UpdateUserRequest request = UserConverter.convertToOuterUser(userRequest);
        UserServiceOuterClass.User response = stub.updateUser(request);

        log.info("Received updated user with username {} from grpc.", response.getUsername());

        return UserConverter.convertToUserResponse(response);
    }

    public void deleteUser(String username) {
        log.info("Deleting user with username {} via grpc...", username);

        UserServiceOuterClass.UserByUsernameRequest request = UserServiceOuterClass.UserByUsernameRequest.newBuilder()
                .setUsername(username).build();
        stub.deleteUser(request);

        log.info("Deleted user with username {}.", username);
    }
}
