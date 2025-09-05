package com.pragma.fc.user_service.domain.usecase;

import com.pragma.fc.user_service.domain.api.IAuthServicePort;
import com.pragma.fc.user_service.domain.api.IUserServicePort;
import com.pragma.fc.user_service.domain.model.User;
import com.pragma.fc.user_service.domain.spi.IAuthenticationPort;
import com.pragma.fc.user_service.domain.spi.IPasswordEncryptorPort;
import com.pragma.fc.user_service.domain.spi.ITokenServicePort;
import com.pragma.fc.user_service.domain.usecase.output.UseCaseUserWithTokenOutput;

import java.util.HashMap;
import java.util.Map;

public class AuthUseCase implements IAuthServicePort {
    private final IAuthenticationPort tokenAuthenticationPort;
    private final ITokenServicePort tokenServicePort;
    private final IUserServicePort userServicePort;
    private final IPasswordEncryptorPort passwordEncryptorPort;

    public AuthUseCase(IAuthenticationPort tokenAuthenticationPort, ITokenServicePort tokenServicePort, IUserServicePort userServicePort, IPasswordEncryptorPort passwordEncryptorPort) {
        this.tokenAuthenticationPort = tokenAuthenticationPort;
        this.tokenServicePort = tokenServicePort;
        this.userServicePort = userServicePort;
        this.passwordEncryptorPort = passwordEncryptorPort;
    }

    @Override
    public UseCaseUserWithTokenOutput login(String email, String password) {
        tokenAuthenticationPort.authenticate(email, password);

        User user = userServicePort.findUserByEmail(email);

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole());

        String accessToken = tokenServicePort.generateAccessToken(user.getEmail(), claims);
        String refreshToken = tokenServicePort.generateRefreshToken(user.getEmail());

        return new UseCaseUserWithTokenOutput(user, accessToken, refreshToken);
    }

    @Override
    public String encryptPassword(String plainPassword) {
        return passwordEncryptorPort.encrypt(plainPassword);
    }
}
