package com.bna.gac.mapper;

import com.bna.gac.dto.ChargeDossierDTO;
import com.bna.gac.entities.ChargeDossier;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChargeDossierMapper {

    ChargeDossierDTO toDto(ChargeDossier entity);

    ChargeDossier toEntity(ChargeDossierDTO dto);

    List<ChargeDossierDTO> toDtoList(List<ChargeDossier> list);
}
