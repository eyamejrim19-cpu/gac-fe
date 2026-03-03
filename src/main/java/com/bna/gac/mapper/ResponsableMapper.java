package com.bna.gac.mapper;


import org.mapstruct.Mapper;
import com.bna.gac.entities.Responsable;
import com.bna.gac.dto.ResponsableDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ResponsableMapper {

    ResponsableDTO toDTO(Responsable responsable);

    Responsable toEntity(ResponsableDTO dto);

    List<ResponsableDTO> toDTOList(List<Responsable> list);
}