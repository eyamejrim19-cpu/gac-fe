package com.bna.gac.mapper;

import com.bna.gac.dto.AdminDTO;
import com.bna.gac.entities.Admin;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface AdminMapper {

    @Mapping(target = "niveauAcces", ignore = true)
    AdminDTO toDto(Admin entity);

    @Mapping(target = "nom", ignore = true)
    @Mapping(target = "prenom", ignore = true)
    @Mapping(target = "telephone", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "gererUtilisateur", ignore = true)
    @Mapping(target = "gererProfil", ignore = true)
    Admin toEntity(AdminDTO dto);

    List<AdminDTO> toDtoList(List<Admin> list);
}

