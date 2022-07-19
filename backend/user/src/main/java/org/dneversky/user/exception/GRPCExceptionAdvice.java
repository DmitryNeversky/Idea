package org.dneversky.user.exception;

import io.grpc.Status;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.stream.Collectors;

@GrpcAdvice
public class GRPCExceptionAdvice {

    @GrpcExceptionHandler(EntityNotFoundException.class)
    public Status handleEntityNotFoundException(EntityNotFoundException e) {
        return Status.NOT_FOUND.withDescription(e.getMessage());
    }

    @GrpcExceptionHandler(EntityExistsException.class)
    public Status handleEntityExistsException(EntityExistsException e) {
        return Status.ALREADY_EXISTS.withDescription(e.getMessage());
    }

    // TODO: test
    @GrpcExceptionHandler(MethodArgumentNotValidException.class)
    public Status handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<String> errorMessages = e.getFieldErrors().stream()
                .map(FieldError::getDefaultMessage).collect(Collectors.toList());

        return Status.FAILED_PRECONDITION.withDescription(e.getMessage())
                .augmentDescription(errorMessages.toString());
    }

    @GrpcExceptionHandler(BadArgumentException.class)
    public Status handleBadArgumentException(BadArgumentException e) {
        return Status.INVALID_ARGUMENT.withDescription(e.getMessage());
    }
}
