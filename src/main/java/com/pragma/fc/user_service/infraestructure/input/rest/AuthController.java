package com.pragma.fc.user_service.infraestructure.input.rest;

import com.pragma.fc.user_service.application.dto.request.LoginRequestDto;
import com.pragma.fc.user_service.application.dto.response.LoginResponseDto;
import com.pragma.fc.user_service.application.handler.IAuthHandler;
import com.pragma.fc.user_service.infraestructure.input.rest.dto.ApiError;
import com.pragma.fc.user_service.infraestructure.input.rest.dto.ApiSuccess;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${app.api.prefix}/auth")
public class AuthController {
    private final IAuthHandler authHandler;

    public AuthController(IAuthHandler authHandler) {
        this.authHandler = authHandler;
    }

    @Operation(summary = "Login all type of users",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Login successfully",
                            content = @Content(schema = @Schema(implementation = LoginResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "User not found",
                            content = @Content(schema = @Schema(implementation = ApiError.class))),
                    @ApiResponse(responseCode = "401", description = "Bad credentials",
                            content = @Content(schema = @Schema(implementation = ApiError.class)))
            }
    )
    @PostMapping("/login")
    public ResponseEntity<ApiSuccess<LoginResponseDto>> handleLogin(@RequestBody @Valid LoginRequestDto dto) {
        LoginResponseDto response = authHandler.login(dto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess<>(
                        "Login successfully",
                        response
                ));
    }
}
