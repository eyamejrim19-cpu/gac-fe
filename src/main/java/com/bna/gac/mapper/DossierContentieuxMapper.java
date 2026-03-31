package com.bna.gac.mapper;

import org.mapstruct.Mapper;
import com.bna.gac.entities.DossierContentieux;
import com.bna.gac.dto.DossierContentieuxDTO;
import org.springframework.stereotype.Service;

import java.util.List;
@Mapper(componentModel = "spring")
public interface DossierContentieuxMapper {

    DossierContentieuxDTO toDto(DossierContentieux dossier);

    DossierContentieux toEntity(DossierContentieuxDTO dto);

    DossierContentieuxDTO toDTO(DossierContentieux d);

    List<DossierContentieuxDTO> toDTOList(List<DossierContentieux> all);

    List<DossierContentieuxDTO> toDtoList(List<DossierContentieux> all);
}