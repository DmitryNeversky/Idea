package org.dneversky.gateway.api.grpc;

import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.dneversky.gateway.UserServiceGrpc;
import org.dneversky.gateway.UserServiceOuterClass;
import org.dneversky.gateway.model.User;
import org.dneversky.gateway.security.UserPrincipal;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class UserServiceGRPC {

    @GrpcClient(value = "localhost:9090")
    private UserServiceGrpc.UserServiceBlockingStub stub;

    public UserPrincipal getUserPrincipalByUsername(String username) {
        log.info("Getting user by username {} via grpc...", username);
        UserServiceOuterClass.UserPrincipalByUsernameRequest request = UserServiceOuterClass.UserPrincipalByUsernameRequest
                .newBuilder().setUsername(username).build();

        UserServiceOuterClass.UserPrincipal response = stub.getUserPrincipalByUsername(request).getUser();

        return UserPrincipal.buildPrincipal(response);
    }

    public List<User> getAllUsers() {
        log.info("Getting all users via grpc...");

        List<UserServiceOuterClass.SimpleUser> response = stub
                .getAllUsers(UserServiceOuterClass.AllUsersRequest.newBuilder().build()).getUsersList();

        log.info("Gotten users via grpc: {}", response);

        return response.stream().map(User::buildUser).collect(Collectors.toList());
    }
}
