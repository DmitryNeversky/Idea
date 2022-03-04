package org.dneversky.idea.exception;

public class EntityExistsException extends RuntimeException {
    public EntityExistsException(String s) {
        super(s);
    }
}
