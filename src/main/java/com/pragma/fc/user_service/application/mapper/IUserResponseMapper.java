package com.pragma.fc.user_service.application.mapper;

import com.pragma.fc.user_service.application.dto.response.UserResponseDto;
import com.pragma.fc.user_service.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = IRoleResponseMapper.class
)
public interface IUserResponseMapper {
    @Mapping(target = "role", source = "role", qualifiedByName = "modelToDto")
    @Named("modelToDto")
    UserResponseDto toDto(User user);
}
