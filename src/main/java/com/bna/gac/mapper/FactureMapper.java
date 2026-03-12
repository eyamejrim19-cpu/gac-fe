package com.bna.gac.mapper;

import com.bna.gac.dto.FactureDTO;
import com.bna.gac.entities.Facture;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FactureMapper {

    @Mapping(source = "mission.idMission", target = "missionId")
    @Mapping(source = "prestataire.idPrestataire", target = "prestataireId")
    FactureDTO toDto(Facture facture);

    @Mapping(target = "mission", ignore = true)
    @Mapping(target = "prestataire", ignore = true)
    Facture toEntity(FactureDTO dto);

    List<FactureDTO> toDtoList(List<Facture> factures);
}
