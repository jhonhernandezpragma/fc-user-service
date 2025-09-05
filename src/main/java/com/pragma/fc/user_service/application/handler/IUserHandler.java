package com.pragma.fc.user_service.application.handler;

import com.pragma.fc.user_service.application.dto.request.CreateOwnerRequestDto;
import com.pragma.fc.user_service.application.dto.response.RoleResponseDto;
import com.pragma.fc.user_service.application.dto.response.UserResponseDto;

public interface IUserHandler {
    UserResponseDto createOwner(CreateOwnerRequestDto dto);

    RoleResponseDto getUserRole(Long documentNumber);
}
