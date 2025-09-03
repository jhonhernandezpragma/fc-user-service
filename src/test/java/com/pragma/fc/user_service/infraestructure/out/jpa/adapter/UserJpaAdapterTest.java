package com.pragma.fc.user_service.infraestructure.out.jpa.adapter;

import com.pragma.fc.user_service.domain.model.User;
import com.pragma.fc.user_service.infraestructure.exception.RoleNotFoundException;
import com.pragma.fc.user_service.infraestructure.out.jpa.entity.RoleEntity;
import com.pragma.fc.user_service.infraestructure.out.jpa.entity.UserEntity;
import com.pragma.fc.user_service.infraestructure.out.jpa.mapper.IUserEntityMapper;
import com.pragma.fc.user_service.infraestructure.out.jpa.repository.IRoleRepository;
import com.pragma.fc.user_service.infraestructure.out.jpa.repository.IUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserJpaAdapterTest {

    @InjectMocks
    private UserJpaAdapter adapter;

    @Mock
    private IUserRepository userRepository;

    @Mock
    private IRoleRepository roleRepository;

    @Mock
    private IUserEntityMapper userEntityMapper;

    @Test
    @DisplayName("Should create user successfully when role exists")
    void shouldCreateUserSuccessfully() {
        User user = new User();
        user.setDocumentNumber(1L);
        user.setName("John");

        UserEntity userEntity = new UserEntity();
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName("OWNER");
        userEntity.setRole(roleEntity);

        UserEntity savedEntity = new UserEntity();
        savedEntity.setRole(roleEntity);

        when(userEntityMapper.toEntity(user)).thenReturn(userEntity);
        when(roleRepository.findByName("OWNER")).thenReturn(Optional.of(roleEntity));
        when(userRepository.save(userEntity)).thenReturn(savedEntity);
        when(userEntityMapper.toModel(savedEntity)).thenReturn(user);

        User result = adapter.createUser(user);

        assertThat(result).isNotNull();
        assertThat(result.getDocumentNumber()).isEqualTo(1L);
        verify(roleRepository).findByName("OWNER");
        verify(userRepository).save(userEntity);
    }

    @Test
    @DisplayName("Should throw RoleNotFoundException when role does not exist")
    void shouldThrowRoleNotFoundException() {
        User user = new User();

        UserEntity userEntity = new UserEntity();
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName("OWNER");
        userEntity.setRole(roleEntity);

        when(userEntityMapper.toEntity(user)).thenReturn(userEntity);
        when(roleRepository.findByName("OWNER")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> adapter.createUser(user))
                .isInstanceOf(RoleNotFoundException.class);

        verify(userRepository, never()).save(any());
    }

    @Test
    void shouldCheckExistUserByEmail() {
        when(userRepository.existsByEmail("test@example.com")).thenReturn(true);
        Boolean exists = adapter.existUserByEmail("test@example.com");
        assertThat(exists).isTrue();
        verify(userRepository).existsByEmail("test@example.com");
    }

    @Test
    void shouldCheckExistUserByDocumentNumber() {
        when(userRepository.existsById(1L)).thenReturn(true);
        Boolean exists = adapter.existUserByDocumentNumber(1L);
        assertThat(exists).isTrue();
        verify(userRepository).existsById(1L);
    }
}
