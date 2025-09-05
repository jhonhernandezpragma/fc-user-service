package com.pragma.fc.user_service.infraestructure.configuration;

import com.pragma.fc.user_service.domain.api.IAuthServicePort;
import com.pragma.fc.user_service.domain.api.IUserServicePort;
import com.pragma.fc.user_service.domain.spi.IAuthenticationPort;
import com.pragma.fc.user_service.domain.spi.IPasswordEncryptorPort;
import com.pragma.fc.user_service.domain.spi.ITokenServicePort;
import com.pragma.fc.user_service.domain.usecase.AuthUseCase;
import com.pragma.fc.user_service.infraestructure.out.security.adapter.AuthenticationAdapter;
import com.pragma.fc.user_service.infraestructure.out.security.adapter.JwtTokenServiceAdapter;
import com.pragma.fc.user_service.infraestructure.out.security.adapter.SpringPasswordEncryptorAdapter;
import com.pragma.fc.user_service.infraestructure.out.security.property.JwtProperties;
import com.pragma.fc.user_service.infraestructure.out.security.service.CustomUserDetailService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@AllArgsConstructor
public class AuthBeanConfiguration {
    private final JwtProperties jwtProperties;

    @Bean
    public IAuthServicePort authServicePort(@Lazy IUserServicePort userServicePort) {
        return new AuthUseCase(authenticationPort(userServicePort), tokenServicePort(), userServicePort, passwordEncryptorPort());
    }

    @Bean
    public ITokenServicePort tokenServicePort() {
        return new JwtTokenServiceAdapter(jwtProperties);
    }

    @Bean
    public IAuthenticationPort authenticationPort(@Lazy IUserServicePort userServicePort) {
        return new AuthenticationAdapter(authenticationManager(userServicePort));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public IPasswordEncryptorPort passwordEncryptorPort() {
        return new SpringPasswordEncryptorAdapter(passwordEncoder());
    }

    @Bean
    public AuthenticationManager authenticationManager(@Lazy IUserServicePort userServicePort) {
        return new ProviderManager(authenticationProvider(userServicePort));
    }

    @Bean
    public AuthenticationProvider authenticationProvider(IUserServicePort userServicePort) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService(userServicePort));
        return provider;
    }

    @Bean
    public UserDetailsService userDetailsService(IUserServicePort userServicePort) {
        return new CustomUserDetailService(userServicePort);
    }
}
