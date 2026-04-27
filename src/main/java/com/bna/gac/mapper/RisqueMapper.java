package com.bna.gac.mapper;

import com.bna.gac.dto.RisqueDTO;
import com.bna.gac.entities.Risque;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface RisqueMapper {

    @Mapping(source = "idRisque", target = "id")
    @Mapping(source = "dossier.idDossier", target = "dossierId")
    RisqueDTO toDTO(Risque risque);

    @Mapping(source = "id", target = "idRisque")
    @Mapping(source = "dossierId", target = "dossier.idDossier")
    @Mapping(target = "garanties", ignore = true)
    Risque toEntity(RisqueDTO dto);

    List<RisqueDTO> toDTOList(List<Risque> all);

    default LocalDate map(LocalDateTime value) {
        return value == null ? null : value.toLocalDate();
    }

    default LocalDateTime map(LocalDate value) {
        return value == null ? null : value.atStartOfDay();
    }

    default String map(Integer value) {
        return value == null ? null : value.toString();
    }

    default Integer map(String value) {
        return value == null || value.isBlank() ? null : Integer.valueOf(value);
    }
}
