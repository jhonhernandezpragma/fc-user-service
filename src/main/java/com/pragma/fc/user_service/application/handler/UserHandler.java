package com.pragma.fc.user_service.application.handler;

import com.pragma.fc.user_service.application.dto.request.CreateUserRequestDto;
import com.pragma.fc.user_service.application.dto.response.RoleResponseDto;
import com.pragma.fc.user_service.application.dto.response.UserResponseDto;
import com.pragma.fc.user_service.application.mapper.ICreateUserRequestMapper;
import com.pragma.fc.user_service.application.mapper.IRoleResponseMapper;
import com.pragma.fc.user_service.application.mapper.IUserResponseMapper;
import com.pragma.fc.user_service.domain.api.IAuthServicePort;
import com.pragma.fc.user_service.domain.api.IUserServicePort;
import com.pragma.fc.user_service.domain.model.Role;
import com.pragma.fc.user_service.domain.model.User;
import com.pragma.fc.user_service.infraestructure.input.security.entity.JwtAuthenticationToken;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserHandler implements IUserHandler {
    private final ICreateUserRequestMapper createUserRequestMapper;
    private final IUserResponseMapper userResponseMapper;
    private final IRoleResponseMapper roleResponseMapper;
    private final IUserServicePort userServicePort;
    private final IAuthServicePort authServicePort;

    @Override
    public UserResponseDto createOwner(CreateUserRequestDto dto) {
        User user = createUserRequestMapper.toModel(dto);
        User newOwner = userServicePort.createOwner(user);
        return userResponseMapper.toDto(newOwner);
    }

    @Override
    public RoleResponseDto getUserRole(Long documentNumber) {
        Role role = userServicePort.getUserRole(documentNumber);
        return roleResponseMapper.toDto(role);
    }

    @Override
    public UserResponseDto createWorker(CreateUserRequestDto dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long ownerDocumentNumber = null;

        if (authentication instanceof JwtAuthenticationToken jwtAuthenticationToken) {
            ownerDocumentNumber = Long.parseLong(authServicePort
                    .extractSubject(jwtAuthenticationToken.getToken())
            );
        }

        User user = createUserRequestMapper.toModel(dto);
        User newOwner = userServicePort.createWorker(ownerDocumentNumber, user);
        return userResponseMapper.toDto(newOwner);
    }
}
