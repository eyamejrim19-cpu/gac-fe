package com.bna.gac.mapper;

import com.bna.gac.dto.HuissierDTO;
import com.bna.gac.entities.Huissier;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface HuissierMapper {

    @Mapping(source = "idPrestataire", target = "id")
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "cabinet", ignore = true)
    HuissierDTO toDto(Huissier entity);

    @Mapping(source = "id", target = "idPrestataire")
    @Mapping(target = "nom", ignore = true)
    @Mapping(target = "prenom", ignore = true)
    @Mapping(target = "telephone", ignore = true)
    @Mapping(target = "adresse", ignore = true)
    @Mapping(target = "specialite", ignore = true)
    @Mapping(target = "tarifJournalier", ignore = true)
    @Mapping(target = "actif", ignore = true)
    @Mapping(target = "typePrestataire", ignore = true)
    @Mapping(target = "zoneIntervention", ignore = true)
    Huissier toEntity(HuissierDTO dto);

    List<HuissierDTO> toDtoList(List<Huissier> list);
}
