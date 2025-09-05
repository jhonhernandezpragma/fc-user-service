package com.pragma.fc.user_service.application.handler;

import com.pragma.fc.user_service.application.dto.request.LoginRequestDto;
import com.pragma.fc.user_service.application.dto.response.LoginResponseDto;
import com.pragma.fc.user_service.application.mapper.ILoginResponseMapper;
import com.pragma.fc.user_service.domain.api.IAuthServicePort;
import com.pragma.fc.user_service.domain.usecase.output.UseCaseUserWithTokenOutput;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthHandler implements IAuthHandler {
    private final IAuthServicePort authServicePort;
    private final ILoginResponseMapper loginResponseMapper;

    @Override
    public LoginResponseDto login(LoginRequestDto dto) {
        UseCaseUserWithTokenOutput response = authServicePort.login(dto.getEmail(), dto.getPassword());
        return loginResponseMapper.toDto(response);
    }
}
