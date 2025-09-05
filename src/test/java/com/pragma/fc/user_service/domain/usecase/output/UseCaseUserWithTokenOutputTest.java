package com.pragma.fc.user_service.domain.usecase.output;

import com.pragma.fc.user_service.domain.model.Role;
import com.pragma.fc.user_service.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class UseCaseUserWithTokenOutputTest {
    private UseCaseUserWithTokenOutput output;
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

        output = new UseCaseUserWithTokenOutput(
                user,
                "access_token",
                "refresh_token"
        );
    }

    @Test
    void shouldCreateUserCorrectlyWithAllFields() {
        assertThat(output.getAccessToken()).isEqualTo("access_token");
        assertThat(output.getRefreshToken()).isEqualTo("refresh_token");
        assertThat(output.getUser().getDocumentNumber()).isEqualTo(12345L);
    }
}
