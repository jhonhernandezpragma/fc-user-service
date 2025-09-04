package com.pragma.fc.user_service.infraestructure.configuration;

import com.pragma.fc.user_service.domain.api.IUserServicePort;
import com.pragma.fc.user_service.domain.spi.IPasswordEncryptorPort;
import com.pragma.fc.user_service.domain.spi.IUserPersistencePort;
import com.pragma.fc.user_service.domain.usecase.UserUseCase;
import com.pragma.fc.user_service.infraestructure.out.jpa.adapter.UserJpaAdapter;
import com.pragma.fc.user_service.infraestructure.out.jpa.mapper.IRoleEntityMapper;
import com.pragma.fc.user_service.infraestructure.out.jpa.mapper.IUserEntityMapper;
import com.pragma.fc.user_service.infraestructure.out.jpa.repository.IRoleRepository;
import com.pragma.fc.user_service.infraestructure.out.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class UserBeanConfiguration {
    private final IUserEntityMapper userEntityMapper;
    private final IRoleEntityMapper roleEntityMapper;
    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final IPasswordEncryptorPort passwordEncryptorPort;

    @Bean
    public IUserServicePort userServicePort() {
        return new UserUseCase(userPersistencePort(), passwordEncryptorPort);
    }

    @Bean
    public IUserPersistencePort userPersistencePort() {
        return new UserJpaAdapter(userRepository, roleRepository, userEntityMapper, roleEntityMapper);
    }
}
