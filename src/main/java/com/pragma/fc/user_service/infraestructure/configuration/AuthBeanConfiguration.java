package com.pragma.fc.user_service.infraestructure.configuration;

import com.pragma.fc.user_service.domain.spi.IPasswordEncryptorPort;
import com.pragma.fc.user_service.infraestructure.out.security.adapter.SpringPasswordEncryptorAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AuthBeanConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public IPasswordEncryptorPort passwordEncryptorPort() {
        return new SpringPasswordEncryptorAdapter(passwordEncoder());
    }
}
