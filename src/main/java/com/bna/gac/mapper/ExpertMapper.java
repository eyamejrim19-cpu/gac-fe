package com.bna.gac.mapper;

import com.bna.gac.dto.ExpertDTO;
import com.bna.gac.entities.Expert;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ExpertMapper {

    @Mapping(source = "idPrestataire", target = "id")
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(source = "domaineExpertise", target = "specialite")
    ExpertDTO toDto(Expert entity);

    @Mapping(source = "id", target = "idPrestataire")
    @Mapping(source = "specialite", target = "domaineExpertise")
    @Mapping(target = "nom", ignore = true)
    @Mapping(target = "prenom", ignore = true)
    @Mapping(target = "telephone", ignore = true)
    @Mapping(target = "adresse", ignore = true)
    @Mapping(target = "tarifJournalier", ignore = true)
    @Mapping(target = "actif", ignore = true)
    @Mapping(target = "typePrestataire", ignore = true)
    @Mapping(target = "rib", ignore = true)
    Expert toEntity(ExpertDTO dto);

    List<ExpertDTO> toDtoList(List<Expert> list);
}
