package com.pragma.fc.user_service.infraestructure.out.jpa.mapper;

import com.pragma.fc.user_service.domain.model.Role;
import com.pragma.fc.user_service.infraestructure.out.jpa.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IRoleEntityMapper {

    @Named("entityToModel")
    default Role toModel(RoleEntity roleEntity) {
        if(roleEntity == null || roleEntity.getName() == null) {
            return null;
        }

        String roleName = roleEntity.getName().replaceFirst("^ROLE_", "");
        return Role.valueOf(roleName);
    }

    @Named("modelToEntity")
    default RoleEntity toEntity(Role role) {
        if(role == null) return null;

        RoleEntity entity = new RoleEntity();
        entity.setName("ROLE_" + role.name());
        return entity;
    }
}
