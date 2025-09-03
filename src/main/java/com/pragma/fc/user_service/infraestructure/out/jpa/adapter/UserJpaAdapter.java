package com.pragma.fc.user_service.infraestructure.out.jpa.adapter;

import com.pragma.fc.user_service.domain.model.User;
import com.pragma.fc.user_service.domain.spi.IUserPersistencePort;
import com.pragma.fc.user_service.infraestructure.out.jpa.entity.RoleEntity;
import com.pragma.fc.user_service.infraestructure.out.jpa.entity.UserEntity;
import com.pragma.fc.user_service.infraestructure.exception.RoleNotFoundException;
import com.pragma.fc.user_service.infraestructure.out.jpa.mapper.IUserEntityMapper;
import com.pragma.fc.user_service.infraestructure.out.jpa.repository.IRoleRepository;
import com.pragma.fc.user_service.infraestructure.out.jpa.repository.IUserRepository;

public class UserJpaAdapter implements IUserPersistencePort {
    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final IUserEntityMapper userEntityMapper;

    public UserJpaAdapter(IUserRepository userRepository, IRoleRepository roleRepository, IUserEntityMapper userEntityMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userEntityMapper = userEntityMapper;
    }

    @Override
    public User createUser(User user) {
        UserEntity userEntity = userEntityMapper.toEntity(user);

        RoleEntity roleEntity = roleRepository.findByName(userEntity.getRole().getName())
                .orElseThrow(() -> new RoleNotFoundException(userEntity.getRole().getName()));

        userEntity.setRole(roleEntity);

        UserEntity newUserEntity = userRepository.save(userEntity);
        return userEntityMapper.toModel(newUserEntity);
    }

    @Override
    public Boolean existUserByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Boolean existUserByDocumentNumber(Long documentNumber) {
        return userRepository.existsById(documentNumber);
    }
}
