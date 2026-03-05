package com.bna.gac.mapper;


import org.mapstruct.*;
import com.bna.gac.entities.DossierContentieux;
import com.bna.gac.dto.DossierContentieuxDTO;

@Mapper(componentModel = "spring")
public interface DossierContentieuxMapper {

    // ENTITY ➜ DTO
    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "chargeDossier.id", target = "chargeDossierId")
    DossierContentieuxDTO toDTO(DossierContentieux entity);

    // DTO ➜ ENTITY
    @Mapping(source = "clientId", target = "client.id")
    @Mapping(source = "chargeDossierId", target = "chargeDossier.id")
    DossierContentieux toEntity(DossierContentieuxDTO dto);
}
