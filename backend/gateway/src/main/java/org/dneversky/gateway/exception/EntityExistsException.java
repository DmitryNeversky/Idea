package org.dneversky.gateway.exception;

public class EntityExistsException extends RuntimeException {
    public EntityExistsException(String s) {
        super(s);
    }
}
