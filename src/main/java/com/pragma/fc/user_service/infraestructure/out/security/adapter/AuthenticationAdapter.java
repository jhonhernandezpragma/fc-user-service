package com.pragma.fc.user_service.infraestructure.out.security.adapter;

import com.pragma.fc.user_service.domain.spi.IAuthenticationPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

@RequiredArgsConstructor
public class AuthenticationAdapter implements IAuthenticationPort {
    private final AuthenticationManager authenticationManage;

    @Override
    public Authentication authenticate(String email, String password) {
        return authenticationManage.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
    }
}
