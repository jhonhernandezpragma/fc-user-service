package com.pragma.fc.user_service.infraestructure.input.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiSuccess<T> {
    private String message;
    private T payload;
}
