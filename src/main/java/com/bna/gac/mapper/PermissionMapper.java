package com.bna.gac.mapper;

import com.bna.gac.dto.PermissionDTO;
import com.bna.gac.entities.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PermissionMapper {

    PermissionDTO toDto(Permission permission);

    Permission toEntity(PermissionDTO dto);
}
