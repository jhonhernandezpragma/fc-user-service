package com.pragma.fc.user_service.domain.usecase;

import com.pragma.fc.user_service.domain.api.IAuthServicePort;
import com.pragma.fc.user_service.domain.api.IUserServicePort;
import com.pragma.fc.user_service.domain.exception.OwnerRestaurantNotFoundException;
import com.pragma.fc.user_service.domain.exception.UserAlreadyExistsException;
import com.pragma.fc.user_service.domain.exception.UserUnderageException;
import com.pragma.fc.user_service.domain.model.Role;
import com.pragma.fc.user_service.domain.model.User;
import com.pragma.fc.user_service.domain.spi.IRestaurantClientPort;
import com.pragma.fc.user_service.domain.spi.IUserPersistencePort;
import com.pragma.fc.user_service.infraestructure.exception.UserNotFoundException;

import java.time.LocalDate;
import java.time.Period;

public class UserUseCase implements IUserServicePort {
    private final IUserPersistencePort userPersistencePort;
    private final IAuthServicePort authServicePort;
    private final IRestaurantClientPort restaurantClientPort;

    public UserUseCase(IUserPersistencePort userPersistencePort, IAuthServicePort authServicePort, IRestaurantClientPort restaurantClientPort) {
        this.userPersistencePort = userPersistencePort;
        this.authServicePort = authServicePort;
        this.restaurantClientPort = restaurantClientPort;
    }

    @Override
    public User createOwner(User user) {
        Boolean existsUserByEmail = userPersistencePort.existUserByEmail(user.getEmail());
        Boolean existsUserByDocumentNumber = userPersistencePort.existUserByDocumentNumber(user.getDocumentNumber());

        if (existsUserByEmail || existsUserByDocumentNumber) {
            throw new UserAlreadyExistsException();
        }

        int age = Period.between(user.getBirthDate(), LocalDate.now()).getYears();
        if (age < 18) {
            throw new UserUnderageException(user.getBirthDate());
        }

        String encryptedPassword = authServicePort.encryptPassword(user.getPassword());
        user.setPassword(encryptedPassword);

        user.setRole(Role.OWNER);
        return userPersistencePort.createUser(user);
    }

    @Override
    public User getUserByDocumentNumber(Long documentNumber) {
        return userPersistencePort.findUserByDocumentNumber(documentNumber);
    }

    @Override
    public User findUserByEmail(String email) {
        return userPersistencePort.findUserByEmail(email);
    }

    @Override
    public User findUserByDocumentNumber(Long documentNumber) {
        return userPersistencePort.findUserByDocumentNumber(documentNumber);
    }

    @Override
    public User createCustomer(User user) {
        Boolean existsUserByEmail = userPersistencePort.existUserByEmail(user.getEmail());
        Boolean existsUserByDocumentNumber = userPersistencePort.existUserByDocumentNumber(user.getDocumentNumber());
        if (existsUserByEmail || existsUserByDocumentNumber) {
            throw new UserAlreadyExistsException("A user with email " + user.getEmail() + " or document " + user.getDocumentNumber() + " already exists");
        }

        String encryptedPassword = authServicePort.encryptPassword(user.getPassword());
        user.setPassword(encryptedPassword);

        user.setRole(Role.CUSTOMER);

        return userPersistencePort.createUser(user);
    }

    @Override
    public User createWorker(Long ownerDocumentNumber, User user) {
        Boolean existsOwnerByDocumentNumber = userPersistencePort.existUserByDocumentNumber(ownerDocumentNumber);
        if (!existsOwnerByDocumentNumber) {
            throw new UserNotFoundException("Owner with document " + ownerDocumentNumber + " does not exist");
        }

        Boolean existsUserByEmail = userPersistencePort.existUserByEmail(user.getEmail());
        Boolean existsUserByDocumentNumber = userPersistencePort.existUserByDocumentNumber(user.getDocumentNumber());
        if (existsUserByEmail || existsUserByDocumentNumber) {
            throw new UserAlreadyExistsException("A user with email " + user.getEmail() + " or document " + user.getDocumentNumber() + " already exists");
        }

        Long restaurantNit = restaurantClientPort.getRestaurantNitByOwner();
        if (restaurantNit == null) {
            throw new OwnerRestaurantNotFoundException(ownerDocumentNumber);
        }

        String encryptedPassword = authServicePort.encryptPassword(user.getPassword());
        user.setPassword(encryptedPassword);

        user.setRole(Role.WORKER);

        User newWorker = userPersistencePort.createUser(user);
        restaurantClientPort.assignWorkerToRestaurant(restaurantNit, newWorker.getDocumentNumber());
        return newWorker;
    }
}
