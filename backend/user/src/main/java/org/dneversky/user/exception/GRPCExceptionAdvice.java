package org.dneversky.user.exception;

import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.StatusException;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;
import org.springframework.http.HttpStatus;

@GrpcAdvice
public class GRPCExceptionAdvice {

    @GrpcExceptionHandler(EntityNotFoundException.class)
    public StatusException handleEntityNotFoundException(EntityNotFoundException e) {
        Metadata metadata = new Metadata();
        metadata.put(Metadata.Key.of("status_code", Metadata.ASCII_STRING_MARSHALLER), HttpStatus.NOT_FOUND.name());
        metadata.put(Metadata.Key.of("message", Metadata.ASCII_STRING_MARSHALLER), e.getMessage());
        return Status.NOT_FOUND.withDescription(e.getMessage()).asException(metadata);
    }

    @GrpcExceptionHandler(EntityExistsException.class)
    public StatusException handleEntityExistsException(EntityExistsException e) {
        Metadata metadata = new Metadata();
        metadata.put(Metadata.Key.of("status_code", Metadata.ASCII_STRING_MARSHALLER), HttpStatus.FOUND.name());
        metadata.put(Metadata.Key.of("message", Metadata.ASCII_STRING_MARSHALLER), e.getMessage());
        return Status.ALREADY_EXISTS.withDescription(e.getMessage()).asException(metadata);
    }

//    // TODO: test
//    @GrpcExceptionHandler(MethodArgumentNotValidException.class)
//    public Status handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
//        List<String> errorMessages = e.getFieldErrors().stream()
//                .map(FieldError::getDefaultMessage).collect(Collectors.toList());
//
//        return Status.FAILED_PRECONDITION.withDescription(e.getMessage())
//                .augmentDescription(errorMessages.toString());
//    }

    @GrpcExceptionHandler(BadArgumentException.class)
    public StatusException handleBadArgumentException(BadArgumentException e) {
        Metadata metadata = new Metadata();
        metadata.put(Metadata.Key.of("status_code", Metadata.ASCII_STRING_MARSHALLER), HttpStatus.BAD_REQUEST.name());
        metadata.put(Metadata.Key.of("message", Metadata.ASCII_STRING_MARSHALLER), e.getMessage());
        return Status.INVALID_ARGUMENT.withDescription(e.getMessage()).asException(metadata);
    }
}
