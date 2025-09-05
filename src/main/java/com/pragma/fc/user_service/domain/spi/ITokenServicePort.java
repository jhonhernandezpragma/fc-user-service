package com.pragma.fc.user_service.domain.spi;

import java.util.Map;

public interface ITokenServicePort {
    String generateAccessToken(String subject, Map<String, Object> claims);

    String generateRefreshToken(String subject);
}
