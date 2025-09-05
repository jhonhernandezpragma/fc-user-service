package com.pragma.fc.user_service.infraestructure.out.jpa.repository;

import com.pragma.fc.user_service.infraestructure.out.jpa.entity.RoleEntity;
import com.pragma.fc.user_service.infraestructure.out.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IUserRepository extends CrudRepository<UserEntity, Long> {
    boolean existsByEmail(String email);

    @Query("SELECT u.role FROM UserEntity u WHERE u.documentNumber = :documentNumber")
    RoleEntity findRoleByUserId(Long documentNumber);

    Optional<UserEntity> findByEmail(String email);
}
