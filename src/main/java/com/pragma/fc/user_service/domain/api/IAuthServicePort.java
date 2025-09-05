package com.pragma.fc.user_service.domain.api;

import com.pragma.fc.user_service.domain.usecase.output.UseCaseUserWithTokenOutput;

public interface IAuthServicePort {
    UseCaseUserWithTokenOutput login(String email, String password);

    String encryptPassword(String plainPassword);
}
