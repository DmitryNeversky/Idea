package org.dneversky.gateway.api.grpc;

import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.dneversky.gateway.UserServiceGrpc;
import org.dneversky.gateway.UserServiceOuterClass;
import org.dneversky.gateway.converter.UserConverter;
import org.dneversky.gateway.model.SaveUserRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class GRPCUserClient {

    @GrpcClient(value = "localhost:9090")
    private UserServiceGrpc.UserServiceBlockingStub stub;

    public List<UserServiceOuterClass.User> getAllUsers() {
        log.info("Getting all users via grpc...");

        UserServiceOuterClass.AllUsersRequest request = UserServiceOuterClass.AllUsersRequest.newBuilder().build();
        UserServiceOuterClass.AllUsersResponse response = stub.getAllUsers(request);

        log.info("Gotten users via grpc: {}", response);

        return response.getUsersList();
    }

    public UserServiceOuterClass.User getUserByUsername(String username) {
        log.info("Getting user by username {} via grpc...", username);

        UserServiceOuterClass.UserByUsernameRequest request = UserServiceOuterClass.UserByUsernameRequest
                .newBuilder().setUsername(username).build();
        UserServiceOuterClass.User response = stub.getUserByUsername(request);

        log.info("Gotten user via grpc: {}", response);

        return response;
    }

    public UserServiceOuterClass.User getUserById(Long id) {
        log.info("Getting user by id {} via grpc...", id);

        UserServiceOuterClass.UserByIdRequest request = UserServiceOuterClass.UserByIdRequest
                .newBuilder().setId(id).build();
        UserServiceOuterClass.User response = stub.getUserById(request);

        log.info("Gotten user via grpc: {}", response);

        return response;
    }

    public UserServiceOuterClass.User saveUser(SaveUserRequest userRequest) {
        log.info("Saving user with username {} via grpc...", userRequest.getUsername());

        UserServiceOuterClass.UserToSave request = UserConverter.convert(userRequest);
        UserServiceOuterClass.User response = stub.saveUser(request);

        log.info("Gotten saved user via grpc: {}", response);

        return response;
    }
}
