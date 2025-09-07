package com.pragma.fc.user_service.application.dto.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class CreateUserRequestDtoTest {

    @Test
    void shouldSerializeOnlyExpectedFields() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        CreateUserRequestDto dto = new CreateUserRequestDto();
        dto.setDocumentNumber(123L);
        dto.setName("John");
        dto.setLastname("Doe");
        dto.setEmail("john@example.com");
        dto.setBirthDate(LocalDate.of(1990, 1, 1));
        dto.setPhone("+57300000000");
        dto.setPassword("Password1@");

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
                        "password"
                );
    }

    @Test
    @DisplayName("Should deserialize birthDate from yyyy-MM-dd string to LocalDate")
    void shouldDeserializeBirthDateCorrectly() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        String json = "{ \"birthDate\": \"1990-01-01\" }";

        CreateUserRequestDto dto = mapper.readValue(json, CreateUserRequestDto.class);

        assertThat(dto.getBirthDate()).isEqualTo(LocalDate.of(1990, 1, 1));
    }

}
