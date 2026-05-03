package com.bna.gac.mapper;

import com.bna.gac.dto.PrestataireDTO;
import com.bna.gac.entities.Prestataire;
import com.bna.gac.entities.TypePrestataire;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface PrestataireMapper {

    @Mapping(source = "idPrestataire", target = "id")
    @Mapping(source = "typePrestataire", target = "type")
    PrestataireDTO toDTO(Prestataire entity);

    @Mapping(source = "id", target = "idPrestataire")
    @Mapping(source = "type", target = "typePrestataire")
    @Mapping(target = "prenom", ignore = true)
    @Mapping(target = "specialite", ignore = true)
    @Mapping(target = "tarifJournalier", ignore = true)
    @Mapping(target = "actif", ignore = true)
    Prestataire toEntity(PrestataireDTO dto);

    List<PrestataireDTO> toDTOList(List<Prestataire> list);

    default String map(TypePrestataire value) {
        return value == null ? null : value.name();
    }

    default TypePrestataire map(String value) {
        return value == null || value.isBlank() ? null : TypePrestataire.valueOf(value);
    }
}

