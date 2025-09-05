package com.pragma.fc.user_service.application.mapper;

import com.pragma.fc.user_service.application.dto.response.LoginResponseDto;
import com.pragma.fc.user_service.domain.usecase.output.UseCaseUserWithTokenOutput;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = IUserResponseMapper.class
)
public interface ILoginResponseMapper {
    @Mapping(target = "user", source = "user", qualifiedByName = "modelToDto")
    LoginResponseDto toDto(UseCaseUserWithTokenOutput output);
}
