package com.pragma.fc.user_service.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {
    private User user;

    @BeforeEach
    void setUp() {
        user = new User(
                12345L,
                "John",
                "Doe",
                "john.doe@example.com",
                LocalDate.of(2020, 5, 10),
                "+57300000000",
                Role.OWNER,
                "passxxx"
        );
    }

    @Test
    void shouldCreateUserCorrectlyWithAllFields() {
        assertThat(user.getDocumentNumber()).isEqualTo(12345L);
        assertThat(user.getName()).isEqualTo("John");
        assertThat(user.getLastname()).isEqualTo("Doe");
        assertThat(user.getEmail()).isEqualTo("john.doe@example.com");
        assertThat(user.getBirthDate()).isEqualTo(LocalDate.of(2020, 5, 10));
        assertThat(user.getPhone()).isEqualTo("+57300000000");
        assertThat(user.getRole()).isEqualTo(Role.OWNER);
        assertThat(user.getPassword()).isEqualTo("passxxx");
    }
}
