package com.pragma.fc.user_service.domain.usecase;

import com.pragma.fc.user_service.domain.api.IUserServicePort;
import com.pragma.fc.user_service.domain.model.Role;
import com.pragma.fc.user_service.domain.model.User;
import com.pragma.fc.user_service.domain.spi.IAuthenticationPort;
import com.pragma.fc.user_service.domain.spi.IPasswordEncryptorPort;
import com.pragma.fc.user_service.domain.spi.ITokenServicePort;
import com.pragma.fc.user_service.domain.usecase.output.UseCaseUserWithTokenOutput;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthUseCaseTest {

    @InjectMocks
    private AuthUseCase authUseCase;

    @Mock
    private IAuthenticationPort tokenAuthenticationPort;

    @Mock
    private ITokenServicePort tokenServicePort;

    @Mock
    private IUserServicePort userServicePort;

    @Mock
    private IPasswordEncryptorPort passwordEncryptorPort;

    @Test
    void login_shouldReturnUserWithTokens() {
        String email = "test@example.com";
        String password = "1234";
        Long documentNumber = 12345L;

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(Role.OWNER);
        user.setDocumentNumber(documentNumber);

        when(userServicePort.findUserByEmail(email)).thenReturn(user);
        when(tokenServicePort.generateAccessToken(eq(documentNumber.toString()), any(Map.class)))
                .thenReturn("access-token");
        when(tokenServicePort.generateRefreshToken(documentNumber.toString()))
                .thenReturn("refresh-token");

        UseCaseUserWithTokenOutput result = authUseCase.login(email, password);

        assertNotNull(result);
        assertEquals(user, result.getUser());
        assertEquals("access-token", result.getAccessToken());
        assertEquals("refresh-token", result.getRefreshToken());

        verify(tokenAuthenticationPort, times(1)).authenticate(email, password);
        verify(userServicePort, times(1)).findUserByEmail(email);
        verify(tokenServicePort, times(1)).generateAccessToken(eq(documentNumber.toString()), any(Map.class));
        verify(tokenServicePort, times(1)).generateRefreshToken(documentNumber.toString());
    }

    @Test
    void encryptPassword_shouldDelegateToEncryptor() {
        String plainPassword = "mypassword";
        when(passwordEncryptorPort.encrypt(plainPassword)).thenReturn("hashed-password");

        String result = authUseCase.encryptPassword(plainPassword);

        assertEquals("hashed-password", result);
        verify(passwordEncryptorPort, times(1)).encrypt(plainPassword);
    }

    @Test
    void extractSubject_shouldDelegateToTokenService() {
        String token = "some-token";
        when(tokenServicePort.extractSubject(token)).thenReturn("subject-value");

        String result = authUseCase.extractSubject(token);

        assertEquals("subject-value", result);
        verify(tokenServicePort, times(1)).extractSubject(token);
    }
}
