package com.pragma.fc.user_service.infraestructure.out.jpa.mapper;

import com.pragma.fc.user_service.domain.model.User;
import com.pragma.fc.user_service.infraestructure.out.jpa.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        uses = IRoleEntityMapper.class
)
public interface IUserEntityMapper {
    @Mapping(target = "role", source = "role", qualifiedByName = "modelToEntity")
    UserEntity toEntity(User user);

    @Mapping(target = "role", source = "role", qualifiedByName = "entityToModel")
    User toModel(UserEntity entity);
}
