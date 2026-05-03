package com.bna.gac.mapper;

import com.bna.gac.dto.DossierContentieuxDTO;
import com.bna.gac.entities.DossierContentieux;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DossierContentieuxMapper {

    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "chargeDossier.id", target = "chargeDossierId")
    DossierContentieuxDTO toDto(DossierContentieux entity);

    @Mapping(source = "clientId", target = "client.id")
    @Mapping(target = "chargeDossier", ignore = true)
    @Mapping(target = "affaires", ignore = true)
    @Mapping(target = "risques", ignore = true)
    DossierContentieux toEntity(DossierContentieuxDTO dto);

    List<DossierContentieuxDTO> toDtoList(List<DossierContentieux> list);
}