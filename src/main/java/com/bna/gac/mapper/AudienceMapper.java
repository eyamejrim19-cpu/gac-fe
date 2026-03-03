package com.bna.gac.mapper;

import com.bna.gac.dto.AudienceDTO;
import com.bna.gac.entities.Audience;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AudienceMapper {

    @Mapping(source = "dossier.idDossier", target = "dossierId")
    AudienceDTO toDto(Audience entity);

    @Mapping(source = "dossierId", target = "dossier.idDossier")
    Audience toEntity(AudienceDTO dto);

    List<AudienceDTO> toDtoList(List<Audience> list);
}