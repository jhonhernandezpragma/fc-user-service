package com.pragma.fc.user_service.domain.spi;

import org.springframework.security.core.Authentication;

public interface IAuthenticationPort {
    Authentication authenticate(String email, String password);
}
