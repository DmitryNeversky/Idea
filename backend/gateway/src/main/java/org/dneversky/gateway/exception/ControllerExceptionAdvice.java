package org.dneversky.gateway.exception;

import io.grpc.Metadata;
import io.grpc.StatusRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionAdvice {

    @ExceptionHandler(UnforeseenException.class)
    public ResponseEntity<?> handleUnforeseenException(UnforeseenException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(PermissionException.class)
    public ResponseEntity<?> handlePermissionException(PermissionException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(StatusRuntimeException.class)
    public ResponseEntity<?> handleStatusRuntimeException(StatusRuntimeException e) {
        Metadata trailers = e.getTrailers();
        String statusCode = trailers.get(Metadata.Key.of("status_code", Metadata.ASCII_STRING_MARSHALLER));
        String message = trailers.get(Metadata.Key.of("message", Metadata.ASCII_STRING_MARSHALLER));
        return new ResponseEntity<>(message, HttpStatus.valueOf(statusCode));
    }
}
