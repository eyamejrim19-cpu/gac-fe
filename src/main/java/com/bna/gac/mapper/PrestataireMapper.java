package com.bna.gac.mapper;


import org.mapstruct.Mapper;
import com.bna.gac.entities.Prestataire;
import com.bna.gac.dto.PrestataireDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PrestataireMapper {

    PrestataireDTO toDTO(Prestataire entity);

    Prestataire toEntity(PrestataireDTO dto);

    List<PrestataireDTO> toDTOList(List<Prestataire> list);
}
