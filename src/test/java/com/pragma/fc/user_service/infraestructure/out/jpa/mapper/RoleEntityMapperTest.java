package com.pragma.fc.user_service.infraestructure.out.jpa.mapper;

import com.pragma.fc.user_service.domain.model.Role;
import com.pragma.fc.user_service.infraestructure.out.jpa.entity.RoleEntity;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RoleEntityMapperTest {

    private final IRoleEntityMapper mapper = new IRoleEntityMapper() {
    };

    @Test
    void shouldMapEntityToModel() {
        RoleEntity entity = new RoleEntity();
        entity.setName("ROLE_OWNER");

        Role result = mapper.toModel(entity);

        assertThat(result).isEqualTo(Role.OWNER);
    }

    @Test
    void shouldMapModelToEntity() {
        Role role = Role.ADMIN;

        RoleEntity result = mapper.toEntity(role);

        assertThat(result.getName()).isEqualTo("ROLE_ADMIN");
    }

    @Test
    void shouldReturnNullWhenEntityIsNull() {
        assertThat(mapper.toModel(null)).isNull();
    }

    @Test
    void shouldReturnNullWhenRoleIsNull() {
        assertThat(mapper.toEntity(null)).isNull();
    }
}
