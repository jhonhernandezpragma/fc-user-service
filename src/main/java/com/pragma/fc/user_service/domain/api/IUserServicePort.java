package com.pragma.fc.user_service.domain.api;

import com.pragma.fc.user_service.domain.model.Role;
import com.pragma.fc.user_service.domain.model.User;

public interface IUserServicePort {
    User createOwner(User user);

    User createWorker(Long ownerDocumentNumber, User user);

    Role getUserRole(Long documentNumber);

    User findUserByEmail(String email);

    User findUserByDocumentNumber(Long documentNumber);
}