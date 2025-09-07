package com.pragma.fc.user_service.infraestructure.out.jpa.adapter;

import com.pragma.fc.user_service.domain.model.Role;
import com.pragma.fc.user_service.domain.model.User;
import com.pragma.fc.user_service.domain.spi.IUserPersistencePort;
import com.pragma.fc.user_service.infraestructure.exception.RoleNotFoundException;
import com.pragma.fc.user_service.infraestructure.exception.UserNotFoundException;
import com.pragma.fc.user_service.infraestructure.out.jpa.entity.RoleEntity;
import com.pragma.fc.user_service.infraestructure.out.jpa.entity.UserEntity;
import com.pragma.fc.user_service.infraestructure.out.jpa.mapper.IRoleEntityMapper;
import com.pragma.fc.user_service.infraestructure.out.jpa.mapper.IUserEntityMapper;
import com.pragma.fc.user_service.infraestructure.out.jpa.repository.IRoleRepository;
import com.pragma.fc.user_service.infraestructure.out.jpa.repository.IUserRepository;

public class UserJpaAdapter implements IUserPersistencePort {
    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final IUserEntityMapper userEntityMapper;
    private final IRoleEntityMapper roleEntityMapper;

    public UserJpaAdapter(IUserRepository userRepository, IRoleRepository roleRepository, IUserEntityMapper userEntityMapper, IRoleEntityMapper roleEntityMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userEntityMapper = userEntityMapper;
        this.roleEntityMapper = roleEntityMapper;
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

    @Override
    public Role getRoleByUserDocumentNumber(Long documentNumber) {
        RoleEntity roleEntity = userRepository.findRoleByUserId(documentNumber);
        return roleEntityMapper.toModel(roleEntity);
    }

    @Override
    public User findUserByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        return userEntityMapper.toModel(userEntity);
    }

    @Override
    public User findUserByDocumentNumber(Long documentNumber) {
        UserEntity userEntity = userRepository.findById(documentNumber)
                .orElseThrow(UserNotFoundException::new);

        return userEntityMapper.toModel(userEntity);
    }
}
