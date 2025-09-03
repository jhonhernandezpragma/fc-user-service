package com.pragma.fc.user_service.domain.usecase;

import com.pragma.fc.user_service.domain.exception.UserAlreadyExistsException;
import com.pragma.fc.user_service.domain.exception.UserUnderageException;
import com.pragma.fc.user_service.domain.model.Role;
import com.pragma.fc.user_service.domain.model.User;
import com.pragma.fc.user_service.domain.spi.IPasswordEncryptorPort;
import com.pragma.fc.user_service.domain.spi.IUserPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {
    @InjectMocks
    private UserUseCase userUseCase;

    @Mock
    private IUserPersistencePort userPersistencePort;

    @Mock
    private IPasswordEncryptorPort passwordEncryptorPort;

    @BeforeEach
    void beforeEach() {
        when(userPersistencePort.existUserByEmail(anyString()))
                .thenReturn(false);

        when(userPersistencePort.existUserByDocumentNumber(anyLong()))
                .thenReturn(false);
    }

    User createUser(Role role) {
        return new User(
                12345L,
                "John",
                "Doe",
                "john.doe@example.com",
                LocalDate.of(2000, 5, 10),
                "+57300000000",
                role,
                "plainpass"
        );
    }

    @Test
    void shouldCreateOwnerUser() {
        when(passwordEncryptorPort.encrypt(anyString()))
                .thenReturn("encryptedPass");

        when(userPersistencePort.createUser(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        User user = createUser(null);

        User newOwner = userUseCase.createOwner(user);

        assertThat(newOwner.getDocumentNumber()).isEqualTo(12345L);
        assertThat(newOwner.getRole()).isEqualTo(Role.OWNER);
        verify(userPersistencePort).createUser(argThat(u ->
                u.getRole() == Role.OWNER &&
                        u.getDocumentNumber() == 12345L
        ));
    }

    @Test
    void shouldCreateOwnerUserEvenIfAnotherRoleIsProvided() {
        when(passwordEncryptorPort.encrypt(anyString()))
                .thenReturn("encryptedPass");

        when(userPersistencePort.createUser(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        User user = createUser(Role.ADMIN);

        User newOwner = userUseCase.createOwner(user);

        assertThat(newOwner.getRole()).isEqualTo(Role.OWNER);
    }

    @Test
    void shouldEncryptPasswordWhenCreatingOwner() {
        when(passwordEncryptorPort.encrypt(anyString()))
                .thenReturn("encryptedPass");

        when(userPersistencePort.createUser(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        User user = createUser(null);

        User newOwner = userUseCase.createOwner(user);

        assertThat(newOwner.getPassword()).isEqualTo("encryptedPass");
    }

    @Test
    void shouldNotCreateOwnerIfEmailAlreadyExists() {
        when(userPersistencePort.existUserByEmail(anyString()))
                .thenReturn(true);

        User user = createUser(null);

        assertThatThrownBy(() -> userUseCase.createOwner(user))
                .isInstanceOf(UserAlreadyExistsException.class);
    }

    @Test
    void shouldNotCreateOwnerIfDocumentNumberAlreadyExists() {
        when(userPersistencePort.existUserByDocumentNumber(anyLong()))
                .thenReturn(true);

        User user = createUser(null);

        assertThatThrownBy(() -> userUseCase.createOwner(user))
                .isInstanceOf(UserAlreadyExistsException.class);
    }


    @Test
    void shouldNotCreateOwnerIfUnderage() {
        User user = createUser(null);
        user.setBirthDate(LocalDate.now());

        assertThatThrownBy(() -> userUseCase.createOwner(user))
                .isInstanceOf(UserUnderageException.class)
                .hasMessageContaining(user.getBirthDate().toString());
    }
}
