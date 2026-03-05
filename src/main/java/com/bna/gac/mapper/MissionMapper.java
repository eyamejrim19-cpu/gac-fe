package com.bna.gac.mapper;

import com.bna.gac.dto.MissionDTO;
import com.bna.gac.entities.Mission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MissionMapper {

    @Mapping(source = "prestataire.idPrestataire", target = "prestataireId")
    MissionDTO toDto(Mission mission);

    @Mapping(source = "prestataireId", target = "prestataire.idPrestataire")
    Mission toEntity(MissionDTO dto);

    List<MissionDTO> toDtoList(List<Mission> missions);
}
