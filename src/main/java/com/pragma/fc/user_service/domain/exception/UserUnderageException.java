package com.pragma.fc.user_service.domain.exception;

import java.time.LocalDate;

public class UserUnderageException extends RuntimeException {
    public UserUnderageException(LocalDate birthDate) {
        super("User must be at least 18 years old. Provided birth date: " + birthDate);
    }
}
