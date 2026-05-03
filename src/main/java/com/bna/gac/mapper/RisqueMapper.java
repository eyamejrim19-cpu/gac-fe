package com.bna.gac.mapper;

import com.bna.gac.dto.RisqueDTO;
import com.bna.gac.entities.Risque;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RisqueMapper {

    @Mapping(source = "idRisque", target = "id")
    @Mapping(source = "dossier.idDossier", target = "dossierId")
    RisqueDTO toDto(Risque risque);

    @Mapping(source = "id", target = "idRisque")
    @Mapping(source = "dossierId", target = "dossier.idDossier")
    @Mapping(target = "garanties", ignore = true)
    Risque toEntity(RisqueDTO dto);

    List<RisqueDTO> toDtoList(List<Risque> all);
}