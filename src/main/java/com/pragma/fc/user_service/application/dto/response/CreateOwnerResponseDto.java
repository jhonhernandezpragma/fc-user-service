package com.pragma.fc.user_service.application.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CreateOwnerResponseDto {
    private Long documentNumber;
    private String name;
    private String lastname;
    private String email;
    private Date birthDate;
    private String phone;
    private RoleResponseDto role;
}
