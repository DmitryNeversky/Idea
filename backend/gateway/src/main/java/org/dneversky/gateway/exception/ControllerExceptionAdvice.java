package org.dneversky.gateway.exception;

import io.grpc.StatusRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionAdvice {

    @ExceptionHandler(StatusRuntimeException.class)
    public ResponseEntity<?> handleStatusRuntimeException(StatusRuntimeException e) {
        return new ResponseEntity<>(e.getStatus().getDescription(), HttpStatus.valueOf(e.getStatus().getCode().name()));
    }
}
