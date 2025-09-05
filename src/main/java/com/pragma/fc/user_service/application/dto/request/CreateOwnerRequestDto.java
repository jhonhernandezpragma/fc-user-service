package com.pragma.fc.user_service.application.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreateOwnerRequestDto {
    @NotNull
    private Long documentNumber;

    @NotBlank
    @NotNull
    @Size(max = 50)
    private String name;

    @NotBlank
    @NotNull
    @Size(max = 50)
    private String lastname;

    @Email
    @NotNull
    private String email;

    @NotNull
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @NotBlank
    @NotNull
    @Pattern(
            regexp = "^\\+?\\d{1,13}$",
            message = "Phone number must be up to 13 digits and can optionally start with a '+'"
    )
    private String phone;

    @NotBlank
    @NotNull
    @Pattern(
            regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,50}$",
            message = "Password must have 8-50 chars, at least 1 uppercase, 1 lowercase, 1 number and 1 special char"
    )
    private String password;
}
