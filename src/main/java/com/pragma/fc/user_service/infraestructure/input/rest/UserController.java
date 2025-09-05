package com.pragma.fc.user_service.infraestructure.input.rest;

import com.pragma.fc.user_service.application.dto.request.CreateOwnerRequestDto;
import com.pragma.fc.user_service.application.dto.response.RoleResponseDto;
import com.pragma.fc.user_service.application.dto.response.UserResponseDto;
import com.pragma.fc.user_service.application.handler.IUserHandler;
import com.pragma.fc.user_service.infraestructure.input.rest.dto.ApiError;
import com.pragma.fc.user_service.infraestructure.input.rest.dto.ApiSuccess;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @Operation(
            summary = "Create user",
            description = "Requires role ADMIN",
            responses = {
                    @ApiResponse(responseCode = "201", description = "User created",
                            content = @Content(schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "409", description = "User already exists",
                            content = @Content(schema = @Schema(implementation = ApiError.class))),
                    @ApiResponse(responseCode = "400", description = """
                            1. Invalid request body format
                            2. User must be at least 18 years old
                            """,
                            content = @Content(schema = @Schema(implementation = ApiError.class))),
                    @ApiResponse(responseCode = "401", description = "User already exists",
                            content = @Content(schema = @Schema(implementation = ApiError.class))),
                    @ApiResponse(responseCode = "403", description = "Missing or invalid access token",
                            content = @Content(schema = @Schema(implementation = ApiError.class))),
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/owner")
    public ResponseEntity<ApiSuccess<UserResponseDto>> createOwner(@RequestBody @Valid CreateOwnerRequestDto dto) {
        UserResponseDto response = userHandler.createOwner(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiSuccess<>(
                        "Owner created successfully",
                        response
                ));
    }

    @Operation(
            summary = "Obtain user",
            description = "Requires role ADMIN",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User role retrieved successfully",
                            content = @Content(schema = @Schema(implementation = RoleResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "User already exists",
                            content = @Content(schema = @Schema(implementation = ApiError.class))),
                    @ApiResponse(responseCode = "403", description = "Missing or invalid access token",
                            content = @Content(schema = @Schema(implementation = ApiError.class)))
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("{documentNumber}/role")
    public ResponseEntity<ApiSuccess<RoleResponseDto>> getUserRole(@PathVariable Long documentNumber) {
        RoleResponseDto response = userHandler.getUserRole(documentNumber);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess<>(
                        "User role retrieved successfully",
                        response
                ));
    }
}
