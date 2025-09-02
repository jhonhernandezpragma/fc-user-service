package com.pragma.fc.user_service.infraestructure.out.security.adapter;

import com.pragma.fc.user_service.domain.spi.IPasswordEncryptorPort;
import org.springframework.security.crypto.password.PasswordEncoder;

public class SpringPasswordEncryptorAdapter implements IPasswordEncryptorPort {
    private final PasswordEncoder passwordEncoder;

    public SpringPasswordEncryptorAdapter(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String encrypt(String plainPassword) {
        return passwordEncoder.encode(plainPassword);
    }
}
