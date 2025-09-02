package com.pragma.fc.user_service.infraestructure.exception;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(String message) {
        super("Default role " + message + " not found in DB ");
    }
}
