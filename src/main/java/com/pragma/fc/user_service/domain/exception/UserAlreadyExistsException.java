package com.pragma.fc.user_service.domain.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException() {
        super("User already exists.");
    }

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
