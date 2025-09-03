package com.pragma.fc.user_service.domain.spi;

import com.pragma.fc.user_service.domain.model.User;

public interface IUserPersistencePort {
    User createUser(User user);
    Boolean existUserByEmail(String email);
    Boolean existUserByDocumentNumber(Long documentNumber);
}
