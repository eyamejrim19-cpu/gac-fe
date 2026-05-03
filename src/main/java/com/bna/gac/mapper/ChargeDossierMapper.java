package com.bna.gac.mapper;

import com.bna.gac.dto.ChargeDossierDTO;
import com.bna.gac.entities.ChargeDossier;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ChargeDossierMapper {

    @Mapping(target = "enabled", ignore = true)
    ChargeDossierDTO toDto(ChargeDossier entity);

    @Mapping(target = "nom", ignore = true)
    @Mapping(target = "prenom", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "dossiers", ignore = true)
    ChargeDossier toEntity(ChargeDossierDTO dto);

    List<ChargeDossierDTO> toDtoList(List<ChargeDossier> list);
}

