package com.pragma.fc.user_service.infraestructure.input.rest;

import com.pragma.fc.user_service.application.dto.request.CreateOwnerRequestDto;
import com.pragma.fc.user_service.application.dto.response.CreateOwnerResponseDto;
import com.pragma.fc.user_service.application.handler.IUserHandler;
import com.pragma.fc.user_service.infraestructure.input.rest.dto.ApiSuccess;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${app.api.prefix}/users")
public class UserController {
    private final IUserHandler userHandler;

    public UserController(IUserHandler userHandler) {
        this.userHandler = userHandler;
    }

    @PostMapping("/owner")
    public ResponseEntity<ApiSuccess<CreateOwnerResponseDto>> createOwner(@RequestBody @Valid CreateOwnerRequestDto dto) {
        CreateOwnerResponseDto response = userHandler.createOwner(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiSuccess<>(
                        "Owner created successfully",
                        response
                ));
    }
}
