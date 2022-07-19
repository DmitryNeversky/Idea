package org.dneversky.user.exception;

public class EntityExistsException extends RuntimeException {
    public EntityExistsException(String s) {
        super(s);
    }
}
