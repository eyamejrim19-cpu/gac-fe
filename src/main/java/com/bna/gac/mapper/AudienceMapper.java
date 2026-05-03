package com.bna.gac.mapper;

import com.bna.gac.dto.AudienceDTO;
import com.bna.gac.entities.Audience;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface AudienceMapper {

    @Mapping(source = "affaire.idAffaire", target = "affaireId")
    AudienceDTO toDto(Audience entity);

    @Mapping(source = "affaireId", target = "affaire.idAffaire")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Audience toEntity(AudienceDTO dto);

    List<AudienceDTO> toDtoList(List<Audience> entities);
}