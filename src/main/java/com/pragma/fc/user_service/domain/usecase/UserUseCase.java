package com.pragma.fc.user_service.domain.usecase;

import com.pragma.fc.user_service.domain.api.IUserServicePort;
import com.pragma.fc.user_service.domain.exception.UserAlreadyExistsException;
import com.pragma.fc.user_service.domain.exception.UserUnderageException;
import com.pragma.fc.user_service.domain.model.Role;
import com.pragma.fc.user_service.domain.model.User;
import com.pragma.fc.user_service.domain.spi.IPasswordEncryptorPort;
import com.pragma.fc.user_service.domain.spi.IUserPersistencePort;

import java.time.LocalDate;
import java.time.Period;

public class UserUseCase implements IUserServicePort {
    private final IUserPersistencePort userPersistencePort;
    private final IPasswordEncryptorPort passwordEncryptorPort;

    public UserUseCase(IUserPersistencePort userPersistencePort, IPasswordEncryptorPort passwordEncryptorPort) {
        this.userPersistencePort = userPersistencePort;
        this.passwordEncryptorPort = passwordEncryptorPort;
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

        String encryptedPassword = passwordEncryptorPort.encrypt(user.getPassword());
        user.setPassword(encryptedPassword);

        user.setRole(Role.OWNER);
        return userPersistencePort.createUser(user);
    }

    @Override
    public Role getUserRole(Long documentNumber) {
        return userPersistencePort.getRoleByUserDocumentNumber(documentNumber);
    }

    @Override
    public User findUserByEmail(String email) {
        return userPersistencePort.findUserByEmail(email);
    }
}
