package com.pragma.fc.user_service.application.handler;

import com.pragma.fc.user_service.application.dto.request.LoginRequestDto;
import com.pragma.fc.user_service.application.dto.response.LoginResponseDto;

public interface IAuthHandler {
    LoginResponseDto login(LoginRequestDto dto);
}
