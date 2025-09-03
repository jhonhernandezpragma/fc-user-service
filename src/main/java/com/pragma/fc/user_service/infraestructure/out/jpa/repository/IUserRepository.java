package com.pragma.fc.user_service.infraestructure.out.jpa.repository;

import com.pragma.fc.user_service.infraestructure.out.jpa.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface IUserRepository extends CrudRepository<UserEntity, Long> {
    boolean existsByEmail(String email);
}
