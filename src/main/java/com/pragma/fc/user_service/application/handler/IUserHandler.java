package com.pragma.fc.user_service.application.handler;

import com.pragma.fc.user_service.application.dto.request.CreateOwnerRequestDto;
import com.pragma.fc.user_service.application.dto.response.CreateOwnerResponseDto;
import com.pragma.fc.user_service.application.dto.response.RoleResponseDto;

public interface IUserHandler {
    CreateOwnerResponseDto createOwner(CreateOwnerRequestDto dto);

    RoleResponseDto getUserRole(Long documentNumber);
}
