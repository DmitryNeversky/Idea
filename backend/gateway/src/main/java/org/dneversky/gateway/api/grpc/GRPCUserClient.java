package org.dneversky.gateway.api.grpc;

import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.dneversky.gateway.UserServiceGrpc;
import org.dneversky.gateway.UserServiceOuterClass;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class GRPCUserClient implements UserClient {

    @GrpcClient(value = "localhost:9090")
    private UserServiceGrpc.UserServiceBlockingStub stub;

    @Override
    public List<UserServiceOuterClass.User> getAllUsers() {
        log.info("Getting all users via grpc...");

        List<UserServiceOuterClass.User> response = stub
                .getAllUsers(UserServiceOuterClass.AllUsersRequest.newBuilder().build()).getUsersList();

        log.info("Gotten users via grpc: {}", response);

        return response;
    }

    @Override
    public UserServiceOuterClass.User getUserByUsername(String username) {
        log.info("Getting user by username {} via grpc...", username);

        UserServiceOuterClass.UserByUsernameRequest request = UserServiceOuterClass.UserByUsernameRequest
                .newBuilder().setUsername(username).build();
        UserServiceOuterClass.User response = stub.getUserByUsername(request);

        log.info("Gotten user via grpc: {}", response);

        return response;
    }

    @Override
    public UserServiceOuterClass.User getUserById(Long id) {
        log.info("Getting user by id {} via grpc...", id);

        UserServiceOuterClass.UserByIdRequest request = UserServiceOuterClass.UserByIdRequest
                .newBuilder().setId(id).build();
        UserServiceOuterClass.User response = stub.getUserById(request);

        log.info("Gotten user via grpc: {}", response);

        return response;
    }
}
