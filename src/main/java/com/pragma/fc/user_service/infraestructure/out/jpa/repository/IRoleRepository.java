package com.pragma.fc.user_service.infraestructure.out.jpa.repository;

import com.pragma.fc.user_service.infraestructure.out.jpa.entity.RoleEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IRoleRepository extends CrudRepository<RoleEntity, Integer> {
    Optional<RoleEntity> findByName(String name);
}
