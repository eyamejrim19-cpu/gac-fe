package com.bna.gac.mapper;

import com.bna.gac.dto.AffaireDTO;
import com.bna.gac.entities.Affaire;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AffaireMapper {

    @Mapping(source = "dossier.idDossier", target = "dossierId")
    @Mapping(source = "prestataire.idPrestataire", target = "prestataireId")
    @Mapping(source = "statut", target = "statut")
    AffaireDTO toDto(Affaire entity);

    @Mapping(source = "dossierId", target = "dossier.idDossier")
    @Mapping(target = "prestataire", ignore = true)
    @Mapping(target = "audiences", ignore = true)
    @Mapping(target = "statut", ignore = true)
    Affaire toEntity(AffaireDTO dto);

    List<AffaireDTO> toDtoList(List<Affaire> entities);
}
