package com.bna.gac.mapper;

import com.bna.gac.dto.FactureDTO;
import com.bna.gac.entities.Facture;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface FactureMapper {

    @Mapping(source = "mission.idMission", target = "missionId")
    @Mapping(source = "idFacture", target = "id")
    @Mapping(target = "dateEcheance", ignore = true)
    @Mapping(target = "dossierId", ignore = true)
    @Mapping(source = "datePaiement", target = "datePaiement")
    @Mapping(source = "typePaiement", target = "typePaiement")
    FactureDTO toDto(Facture entity);

    @Mapping(target = "mission", ignore = true)
    @Mapping(target = "idFacture", ignore = true)
    @Mapping(target = "datePaiement", ignore = true)
    @Mapping(source = "typePaiement", target = "typePaiement")
    Facture toEntity(FactureDTO dto);

    default LocalDate map(LocalDateTime value) {
        return value == null ? null : value.toLocalDate();
    }

    default LocalDateTime map(LocalDate value) {
        return value == null ? null : value.atStartOfDay();
    }
}
