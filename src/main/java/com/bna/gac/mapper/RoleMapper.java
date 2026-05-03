package com.bna.gac.mapper;

import com.bna.gac.dto.RoleDTO;
import com.bna.gac.entities.Permission;
import com.bna.gac.entities.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper {

    @Mapping(target = "permissionIds", source = "permissions", qualifiedByName = "permissionsToIds")
    RoleDTO toDto(Role role);

    Role toEntity(RoleDTO dto);

    @Named("permissionsToIds")
    default Set<Long> permissionsToIds(Set<Permission> permissions) {
        if (permissions == null) {
            return null;
        }
        return permissions.stream()
                .map(Permission::getIdPermission)
                .collect(Collectors.toSet());
    }
}
