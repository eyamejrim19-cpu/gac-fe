package com.bna.gac.mapper;

import com.bna.gac.dto.MissionDTO;
import com.bna.gac.entities.Mission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface MissionMapper {

    @Mapping(source = "prestataire.idPrestataire", target = "prestataireId")
    @Mapping(source = "affaire.idAffaire", target = "affaireId")
    MissionDTO toDto(Mission entity);

    @Mapping(source = "prestataireId", target = "prestataire.idPrestataire")
    @Mapping(source = "affaireId", target = "affaire.idAffaire")
    @Mapping(target = "factures", ignore = true)
    Mission toEntity(MissionDTO dto);

    List<MissionDTO> toDtoList(List<Mission> list);

    default LocalDate map(LocalDateTime value) {
        return value == null ? null : value.toLocalDate();
    }

    default LocalDateTime map(LocalDate value) {
        return value == null ? null : value.atStartOfDay();
    }
}
