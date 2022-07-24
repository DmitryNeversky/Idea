package org.dneversky.user.api.grpc;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.dneversky.gateway.UserServiceGrpc;
import org.dneversky.gateway.UserServiceOuterClass;
import org.dneversky.user.converter.UserConverter;
import org.dneversky.user.entity.User;
import org.dneversky.user.service.impl.DefaultUserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@GrpcService
public class UserServiceGRPC extends UserServiceGrpc.UserServiceImplBase {

    private final DefaultUserService userService;

    @Autowired
    public UserServiceGRPC(DefaultUserService userService) {
        this.userService = userService;
    }

    @Override
    public void getUserByUsername(UserServiceOuterClass.UserByUsernameRequest request, StreamObserver<UserServiceOuterClass.User> responseObserver) {
        log.info("Received request with username '{}' in getUserByUsername() method.", request.getUsername());

        User user = userService.getUserByUsername(request.getUsername());
        UserServiceOuterClass.User response = UserConverter.convert(user);

        responseObserver.onNext(response);
        responseObserver.onCompleted();

        log.info("Returned user with username '{}' from getUserByUsername() method.", request.getUsername());
    }

    @Override
    public void getUserPrincipalByUsername(UserServiceOuterClass.UserByUsernameRequest request, StreamObserver<UserServiceOuterClass.UserPrincipal> responseObserver) {
        log.info("Received request with username '{}' in getUserPrincipalByUsername() method.", request.getUsername());

        User user = userService.getUserByUsername(request.getUsername());
        UserServiceOuterClass.UserPrincipal response = UserConverter.convertToUserPrincipal(user);

        responseObserver.onNext(response);
        responseObserver.onCompleted();

        log.info("Returned user with username '{}' from getUserByUsername() method.", request.getUsername());
    }

    @Override
    public void getAllUsers(UserServiceOuterClass.AllUsersRequest request, StreamObserver<UserServiceOuterClass.AllUsersResponse> responseObserver) {
        log.info("Received request in getAllUsers() method.");

        List<User> users = userService.getAllUsers();
        UserServiceOuterClass.AllUsersResponse response = UserServiceOuterClass.AllUsersResponse.newBuilder()
                .addAllUsers(users.stream().map(UserConverter::convert).collect(Collectors.toList())).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();

        log.info("Returned users from getAllUsers() method.");
    }

    @Override
    public void getUserById(UserServiceOuterClass.UserByIdRequest request, StreamObserver<UserServiceOuterClass.User> responseObserver) {
        log.info("Received request with id '{}' in getUserById() method.", request.getId());

        User user = userService.getUserById(request.getId());
        UserServiceOuterClass.User response = UserConverter.convert(user);

        responseObserver.onNext(response);
        responseObserver.onCompleted();

        log.info("Returned user with id '{}' from getUserById() method.", request.getId());
    }

    @Override
    public void saveUser(UserServiceOuterClass.SaveUserRequest request, StreamObserver<UserServiceOuterClass.User> responseObserver) {
        log.info("Received request for saving user with username '{}' in saveUser() method.", request.getUsername());

        User user = userService.saveUser(UserConverter.convertToSaveUserRequest(request));
        UserServiceOuterClass.User response = UserConverter.convert(user);

        responseObserver.onNext(response);
        responseObserver.onCompleted();

        log.info("Returned user with username '{}' from saveUser() method.", request.getUsername());
    }

    @Override
    public void updateUser(UserServiceOuterClass.UpdateUserRequest request, StreamObserver<UserServiceOuterClass.User> responseObserver) {
        log.info("Received request for update user with username '{}' in saveUser() method.", request.getUsername());

        User user = userService.updateUser(request.getUsername(), UserConverter.convert(request));
        UserServiceOuterClass.User response = UserConverter.convert(user);

        responseObserver.onNext(response);
        responseObserver.onCompleted();

        log.info("Returned user with username '{}' from updateUser() method.", request.getUsername());
    }

    @Override
    public void deleteUser(UserServiceOuterClass.UserByUsernameRequest request, StreamObserver<UserServiceOuterClass.DeleteUserResponse> responseObserver) {
        log.info("Received request for delete user with username '{}' in deleteUser() method.", request.getUsername());

        userService.deleteUser(request.getUsername());
        UserServiceOuterClass.DeleteUserResponse response = UserServiceOuterClass.DeleteUserResponse.newBuilder().build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();

        log.info("Deleted user with username '{}' from deleteUser() method.", request.getUsername());
    }
}
