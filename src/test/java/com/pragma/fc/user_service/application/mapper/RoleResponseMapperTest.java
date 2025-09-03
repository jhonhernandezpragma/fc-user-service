package com.pragma.fc.user_service.application.mapper;

import com.pragma.fc.user_service.application.dto.response.RoleResponseDto;
import com.pragma.fc.user_service.domain.model.Role;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RoleResponseMapperTest {

    private final IRoleResponseMapper mapper = new IRoleResponseMapper() {
    };

    @Test
    void shouldMapRoleToDto() {
        Role role = Role.OWNER;

        RoleResponseDto dto = mapper.toDto(role);

        assertThat(dto).isNotNull();
        assertThat(dto.getName()).isEqualTo("OWNER");
    }

    @Test
    void shouldReturnNullWhenRoleIsNull() {
        RoleResponseDto dto = mapper.toDto(null);
        assertThat(dto).isNull();
    }
}