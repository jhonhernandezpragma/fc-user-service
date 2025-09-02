package com.pragma.fc.user_service.application.mapper;

import com.pragma.fc.user_service.application.dto.response.RoleResponseDto;
import com.pragma.fc.user_service.domain.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface IRoleResponseMapper {

    @Named("modelToDto")
    default RoleResponseDto toDto(Role role) {
        if(role == null) return null;

        RoleResponseDto dto = new RoleResponseDto();
        dto.setName(role.name());
        return dto;
    }
}
