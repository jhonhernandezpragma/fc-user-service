package com.pragma.fc.user_service.application.dto.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class UserResponseDtoTest {

    @Test
    void shouldSerializeOnlyExpectedFields() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        RoleResponseDto roleDto = new RoleResponseDto();
        roleDto.setName("OWNER");

        UserResponseDto dto = new UserResponseDto();
        dto.setDocumentNumber(123L);
        dto.setName("John");
        dto.setLastname("Doe");
        dto.setEmail("john@example.com");
        dto.setBirthDate(LocalDate.now());
        dto.setPhone("+57300000000");
        dto.setRole(roleDto);

        String json = mapper.writeValueAsString(dto);
        Map<String, Object> map = mapper.readValue(json, new TypeReference<Map<String, Object>>() {
        });

        assertThat(map.keySet())
                .containsExactlyInAnyOrder(
                        "documentNumber",
                        "name",
                        "lastname",
                        "email",
                        "birthDate",
                        "phone",
                        "role"
                );
    }

    @Test
    @DisplayName("Should serialize birthDate in yyyy-MM-dd format")
    void shouldSerializeBirthDateCorrectly() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        UserResponseDto dto = new UserResponseDto();
        dto.setBirthDate(LocalDate.of(1990, 1, 1));

        String json = mapper.writeValueAsString(dto);
        assertThat(json).contains("\"birthDate\":\"1990-01-01\"");
    }
}
