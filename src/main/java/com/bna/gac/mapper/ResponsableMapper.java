package com.bna.gac.mapper;

import com.bna.gac.dto.ResponsableDTO;
import com.bna.gac.entities.Responsable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ResponsableMapper {

    ResponsableDTO toDTO(Responsable responsable);

    @Mapping(target = "username", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "validerDossier", ignore = true)
    @Mapping(target = "validerFacture", ignore = true)
    @Mapping(target = "validerMission", ignore = true)
    @Mapping(target = "validerFactures", ignore = true)
    @Mapping(target = "consulterStatistic", ignore = true)
    Responsable toEntity(ResponsableDTO dto);

    List<ResponsableDTO> toDTOList(List<Responsable> list);
}

