package org.dneversky.gateway.exception;

import io.grpc.Metadata;
import io.grpc.StatusRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionAdvice {

    @ExceptionHandler(StatusRuntimeException.class)
    public ResponseEntity<?> handleStatusRuntimeException(StatusRuntimeException e) {
        Metadata trailers = e.getTrailers();
        String statusCode = trailers.get(Metadata.Key.of("status_code", Metadata.ASCII_STRING_MARSHALLER));
        String message = trailers.get(Metadata.Key.of("message", Metadata.ASCII_STRING_MARSHALLER));
        return new ResponseEntity<>(message, HttpStatus.valueOf(statusCode));
    }
}
