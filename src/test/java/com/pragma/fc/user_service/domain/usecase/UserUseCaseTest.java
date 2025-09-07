package com.pragma.fc.user_service.domain.usecase;

import com.pragma.fc.user_service.domain.api.IAuthServicePort;
import com.pragma.fc.user_service.domain.exception.OwnerRestaurantNotFoundException;
import com.pragma.fc.user_service.domain.exception.UserAlreadyExistsException;
import com.pragma.fc.user_service.domain.exception.UserUnderageException;
import com.pragma.fc.user_service.domain.model.Role;
import com.pragma.fc.user_service.domain.model.User;
import com.pragma.fc.user_service.domain.spi.IRestaurantClientPort;
import com.pragma.fc.user_service.domain.spi.IUserPersistencePort;
import com.pragma.fc.user_service.infraestructure.exception.UserNotFoundException;
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
    private IAuthServicePort authServicePort;

    @Mock
    private IRestaurantClientPort restaurantClientPort;

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
        when(userPersistencePort.existUserByEmail(anyString())).thenReturn(false);
        when(userPersistencePort.existUserByDocumentNumber(anyLong())).thenReturn(false);
        when(authServicePort.encryptPassword(anyString())).thenReturn("encryptedPass");
        when(userPersistencePort.createUser(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

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
        when(userPersistencePort.existUserByEmail(anyString())).thenReturn(false);
        when(userPersistencePort.existUserByDocumentNumber(anyLong())).thenReturn(false);
        when(authServicePort.encryptPassword(anyString())).thenReturn("encryptedPass");
        when(userPersistencePort.createUser(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User user = createUser(Role.ADMIN);
        User newOwner = userUseCase.createOwner(user);

        assertThat(newOwner.getRole()).isEqualTo(Role.OWNER);
    }

    @Test
    void shouldEncryptPasswordWhenCreatingOwner() {
        when(userPersistencePort.existUserByEmail(anyString())).thenReturn(false);
        when(userPersistencePort.existUserByDocumentNumber(anyLong())).thenReturn(false);
        when(authServicePort.encryptPassword(anyString())).thenReturn("encryptedPass");
        when(userPersistencePort.createUser(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User user = createUser(null);
        User newOwner = userUseCase.createOwner(user);

        assertThat(newOwner.getPassword()).isEqualTo("encryptedPass");
    }

    @Test
    void shouldNotCreateOwnerIfEmailAlreadyExists() {
        when(userPersistencePort.existUserByEmail(anyString())).thenReturn(true);

        User user = createUser(null);

        assertThatThrownBy(() -> userUseCase.createOwner(user))
                .isInstanceOf(UserAlreadyExistsException.class);
    }

    @Test
    void shouldNotCreateOwnerIfDocumentNumberAlreadyExists() {
        when(userPersistencePort.existUserByDocumentNumber(anyLong())).thenReturn(true);

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

    @Test
    void shouldReturnUserRole() {
        when(userPersistencePort.getRoleByUserDocumentNumber(12345L)).thenReturn(Role.ADMIN);

        Role role = userUseCase.getUserRole(12345L);

        assertThat(role).isEqualTo(Role.ADMIN);
    }


    @Test
    void shouldFindUserByEmail() {
        User user = createUser(Role.CUSTOMER);
        when(userPersistencePort.findUserByEmail("john.doe@example.com")).thenReturn(user);

        User result = userUseCase.findUserByEmail("john.doe@example.com");

        assertThat(result).isEqualTo(user);
    }

    @Test
    void shouldFindUserByDocumentNumber() {
        User user = createUser(Role.CUSTOMER);
        when(userPersistencePort.findUserByDocumentNumber(12345L)).thenReturn(user);

        User result = userUseCase.findUserByDocumentNumber(12345L);

        assertThat(result).isEqualTo(user);
    }

    @Test
    void shouldCreateWorkerSuccessfully() {
        User worker = createUser(null);
        worker.setEmail("worker@example.com");
        worker.setDocumentNumber(67890L);

        when(userPersistencePort.existUserByDocumentNumber(12345L)).thenReturn(true);
        when(userPersistencePort.existUserByEmail(worker.getEmail())).thenReturn(false);
        when(userPersistencePort.existUserByDocumentNumber(worker.getDocumentNumber())).thenReturn(false);
        when(authServicePort.encryptPassword(anyString())).thenReturn("encryptedPass");
        when(userPersistencePort.createUser(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(restaurantClientPort.getRestaurantNitByOwner()).thenReturn(111L);

        User newWorker = userUseCase.createWorker(12345L, worker);

        assertThat(newWorker.getRole()).isEqualTo(Role.WORKER);
        verify(restaurantClientPort).assignWorkerToRestaurant(111L, 67890L);
    }


    @Test
    void shouldNotCreateWorkerIfOwnerDoesNotExist() {
        when(userPersistencePort.existUserByDocumentNumber(12345L)).thenReturn(false);

        User worker = createUser(null);

        assertThatThrownBy(() -> userUseCase.createWorker(12345L, worker))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("Owner with document 12345 does not exist");
    }

    @Test
    void shouldNotCreateWorkerIfEmailOrDocumentAlreadyExists() {
        when(userPersistencePort.existUserByDocumentNumber(12345L)).thenReturn(true);
        when(userPersistencePort.existUserByEmail(anyString())).thenReturn(true);

        User worker = createUser(null);
        worker.setEmail("worker@example.com");
        worker.setDocumentNumber(67890L);

        assertThatThrownBy(() -> userUseCase.createWorker(12345L, worker))
                .isInstanceOf(UserAlreadyExistsException.class)
                .hasMessageContaining(worker.getEmail());
    }

    @Test
    void shouldNotCreateWorkerIfRestaurantNotFound() {
        User worker = createUser(null);
        worker.setEmail("worker@example.com");
        worker.setDocumentNumber(67890L);

        when(userPersistencePort.existUserByDocumentNumber(12345L)).thenReturn(true);
        when(userPersistencePort.existUserByEmail(worker.getEmail())).thenReturn(false);
        when(userPersistencePort.existUserByDocumentNumber(worker.getDocumentNumber())).thenReturn(false);
        when(restaurantClientPort.getRestaurantNitByOwner()).thenReturn(null);

        assertThatThrownBy(() -> userUseCase.createWorker(12345L, worker))
                .isInstanceOf(OwnerRestaurantNotFoundException.class)
                .hasMessageContaining("12345");
    }

    @Test
    void shouldCreateCustomerSuccessfully() {
        when(userPersistencePort.existUserByEmail(anyString())).thenReturn(false);
        when(userPersistencePort.existUserByDocumentNumber(anyLong())).thenReturn(false);
        when(authServicePort.encryptPassword(anyString())).thenReturn("encryptedPass");
        when(userPersistencePort.createUser(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User user = createUser(null); // role null, será asignado como CUSTOMER
        User newCustomer = userUseCase.createCustomer(user);

        assertThat(newCustomer.getRole()).isEqualTo(Role.CUSTOMER);
        assertThat(newCustomer.getPassword()).isEqualTo("encryptedPass");
        verify(userPersistencePort).createUser(argThat(u ->
                u.getRole() == Role.CUSTOMER &&
                        u.getDocumentNumber() == 12345L
        ));
    }

    @Test
    void shouldNotCreateCustomerIfEmailAlreadyExists() {
        when(userPersistencePort.existUserByEmail(anyString())).thenReturn(true);

        User user = createUser(null);

        assertThatThrownBy(() -> userUseCase.createCustomer(user))
                .isInstanceOf(UserAlreadyExistsException.class)
                .hasMessageContaining(user.getEmail());
    }

    @Test
    void shouldNotCreateCustomerIfDocumentNumberAlreadyExists() {
        when(userPersistencePort.existUserByEmail(anyString())).thenReturn(false);
        when(userPersistencePort.existUserByDocumentNumber(anyLong())).thenReturn(true);

        User user = createUser(null);

        assertThatThrownBy(() -> userUseCase.createCustomer(user))
                .isInstanceOf(UserAlreadyExistsException.class)
                .hasMessageContaining(user.getDocumentNumber().toString());
    }

}
