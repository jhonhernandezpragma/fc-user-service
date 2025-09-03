package com.pragma.fc.user_service.infraestructure.out.security.adapter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SpringPasswordEncryptorAdapterTest {

    @InjectMocks
    private SpringPasswordEncryptorAdapter encryptor;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void shouldEncryptPassword() {
        String plainPassword = "Password1@";
        String encodedPassword = "encodedPassword";

        when(passwordEncoder.encode(plainPassword)).thenReturn(encodedPassword);

        String result = encryptor.encrypt(plainPassword);

        assertThat(result).isEqualTo(encodedPassword);
        verify(passwordEncoder, times(1)).encode(plainPassword);
    }
}
