package com.bna.gac.mapper;

import com.bna.gac.dto.AvocatDTO;
import com.bna.gac.entities.Avocat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface AvocatMapper {

    @Mapping(source = "idPrestataire", target = "id")
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "domaine", ignore = true)
    AvocatDTO toDto(Avocat entity);

    @Mapping(source = "id", target = "idPrestataire")
    @Mapping(target = "nom", ignore = true)
    @Mapping(target = "prenom", ignore = true)
    @Mapping(target = "telephone", ignore = true)
    @Mapping(target = "adresse", ignore = true)
    @Mapping(target = "specialite", ignore = true)
    @Mapping(target = "tarifJournalier", ignore = true)
    @Mapping(target = "actif", ignore = true)
    @Mapping(target = "typePrestataire", ignore = true)
    @Mapping(target = "rib", ignore = true)
    Avocat toEntity(AvocatDTO dto);

    List<AvocatDTO> toDtoList(List<Avocat> list);
}
