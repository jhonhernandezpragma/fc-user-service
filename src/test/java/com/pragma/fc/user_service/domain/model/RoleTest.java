package com.pragma.fc.user_service.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class RoleTest {

    @Test
    @DisplayName("Should have exactly 4 roles with correct names")
    void shouldHaveAllRoles() {
        assertThat(Role.values())
                .hasSize(4)
                .containsExactly(
                        Role.ADMIN,
                        Role.OWNER,
                        Role.WORKER,
                        Role.CUSTOMER
                );
    }

    @Test
    void shouldContainExpectedRolesNames() {
        assertThat(Arrays.stream(Role.values())
                .map(Enum::name)
                .toList()
        ).containsExactly("ADMIN", "OWNER", "WORKER", "CUSTOMER");
    }
}
