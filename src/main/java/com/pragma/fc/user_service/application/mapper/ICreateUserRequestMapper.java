package com.pragma.fc.user_service.application.mapper;

import com.pragma.fc.user_service.application.dto.request.CreateUserRequestDto;
import com.pragma.fc.user_service.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ICreateUserRequestMapper {
    User toModel(CreateUserRequestDto dto);
}
