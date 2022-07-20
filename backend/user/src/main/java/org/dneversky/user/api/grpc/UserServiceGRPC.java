package org.dneversky.user.api.grpc;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.dneversky.gateway.UserServiceGrpc;
import org.dneversky.gateway.UserServiceOuterClass;
import org.dneversky.user.converter.UserGRPCConverter;
import org.dneversky.user.entity.User;
import org.dneversky.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;
import java.util.stream.Collectors;

@GrpcService
public class UserServiceGRPC extends UserServiceGrpc.UserServiceImplBase {

    private final UserService userService;

    @Autowired
    public UserServiceGRPC(@Qualifier("userServiceImpl") UserService userService) {
        this.userService = userService;
    }

    @Override
    public void getUserByUsername(UserServiceOuterClass.UserByUsernameRequest request, StreamObserver<UserServiceOuterClass.User> responseObserver) {
        User user = userService.getUserByUsername(request.getUsername());

        UserServiceOuterClass.User response = UserGRPCConverter.convert(user);

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getAllUsers(UserServiceOuterClass.AllUsersRequest request, StreamObserver<UserServiceOuterClass.AllUsersResponse> responseObserver) {
        List<User> users = userService.getAllUsers();

        UserServiceOuterClass.AllUsersResponse response = UserServiceOuterClass.AllUsersResponse.newBuilder()
                .addAllUsers(users.stream().map(UserGRPCConverter::convert).collect(Collectors.toList())).build();

        System.out.println(response);

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getUserById(UserServiceOuterClass.UserByIdRequest request, StreamObserver<UserServiceOuterClass.User> responseObserver) {
        User user = userService.getUser(request.getId());

        UserServiceOuterClass.User response = UserGRPCConverter.convert(user);

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
