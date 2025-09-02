package com.pragma.fc.user_service.application.handler;

import com.pragma.fc.user_service.application.dto.request.CreateOwnerRequestDto;
import com.pragma.fc.user_service.application.dto.response.CreateOwnerResponseDto;
import com.pragma.fc.user_service.application.mapper.ICreateOwnerRequestMapper;
import com.pragma.fc.user_service.application.mapper.ICreateOwnerResponseMapper;
import com.pragma.fc.user_service.domain.api.IUserServicePort;
import com.pragma.fc.user_service.domain.model.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Transactional
@RequiredArgsConstructor
public class UserHandler implements IUserHandler {
    private final ICreateOwnerRequestMapper createOwnerRequestMapper;
    private final ICreateOwnerResponseMapper createOwnerResponseMapper;
    private final IUserServicePort userServicePort;

    @Override
    public CreateOwnerResponseDto createOwner(CreateOwnerRequestDto dto) {
        User user = createOwnerRequestMapper.toModel(dto);
        User newOwner = userServicePort.createOwner(user);
        return createOwnerResponseMapper.toDto(newOwner);
    }
}
