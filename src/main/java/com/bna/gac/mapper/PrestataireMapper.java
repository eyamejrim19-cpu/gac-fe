package com.bna.gac.mapper;

import com.bna.gac.dto.PrestataireDTO;
import com.bna.gac.entities.Prestataire;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PrestataireMapper {

    PrestataireDTO toDTO(Prestataire entity);

    Prestataire toEntity(PrestataireDTO dto);

    List<PrestataireDTO> toDTOList(List<Prestataire> list);

    /**
     * Updates an existing entity from a DTO, ignoring null values for id and timestamps.
     */
    void updateEntityFromDTO(PrestataireDTO dto, @MappingTarget Prestataire entity);
}
