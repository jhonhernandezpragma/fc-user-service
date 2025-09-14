package com.pragma.fc.user_service.application.handler;

import com.pragma.fc.user_service.application.dto.request.CreateUserRequestDto;
import com.pragma.fc.user_service.application.dto.response.UserResponseDto;

public interface IUserHandler {
    UserResponseDto createOwner(CreateUserRequestDto dto);

    UserResponseDto getUserByDocumentNumber(Long documentNumber);

    UserResponseDto createWorker(CreateUserRequestDto dto);

    UserResponseDto createCustomer(CreateUserRequestDto dto);
}
