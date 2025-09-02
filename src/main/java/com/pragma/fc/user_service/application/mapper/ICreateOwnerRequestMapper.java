package com.pragma.fc.user_service.application.mapper;

import com.pragma.fc.user_service.application.dto.request.CreateOwnerRequestDto;
import com.pragma.fc.user_service.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ICreateOwnerRequestMapper {
    CreateOwnerRequestDto toDto(User user);
    User toModel(CreateOwnerRequestDto dto);
}
