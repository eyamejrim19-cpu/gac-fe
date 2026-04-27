package com.bna.gac.mapper;

import com.bna.gac.dto.AffaireDTO;
import com.bna.gac.entities.Affaire;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface AffaireMapper {

    @Mapping(source = "dossier.idDossier", target = "dossierId")
    AffaireDTO toDto(Affaire entity);

    @Mapping(source = "dossierId", target = "dossier.idDossier")
    @Mapping(target = "missions", ignore = true)
    @Mapping(target = "audiences", ignore = true)
    Affaire toEntity(AffaireDTO dto);

    List<AffaireDTO> toDtoList(List<Affaire> entities);

    default LocalDate map(LocalDateTime value) {
        return value == null ? null : value.toLocalDate();
    }

    default LocalDateTime map(LocalDate value) {
        return value == null ? null : value.atStartOfDay();
    }
}
