package org.dneversky.user.api.grpc;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.dneversky.gateway.UserServiceGrpc;
import org.dneversky.gateway.UserServiceOuterClass;
import org.dneversky.user.entity.User;

@GrpcService
public class UserServiceGRPC extends UserServiceGrpc.UserServiceImplBase {

    @Override
    public void getUserByUsername(UserServiceOuterClass.UserByUsernameRequest request, StreamObserver<UserServiceOuterClass.User> responseObserver) {
        System.out.println(request);

        User user = new User();
        user.setUsername("Alex");
        user.setPassword("123");

        UserServiceOuterClass.User response = UserServiceOuterClass.User
                .newBuilder().setUsername("Alex").build();

        responseObserver.onNext(response);

        responseObserver.onCompleted();
    }
}
