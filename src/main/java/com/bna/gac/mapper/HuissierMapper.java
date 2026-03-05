package com.bna.gac.mapper;

import com.bna.gac.dto.HuissierDTO;
import com.bna.gac.entities.Huissier;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HuissierMapper {

    HuissierDTO toDto(Huissier entity);

    Huissier toEntity(HuissierDTO dto);

    List<HuissierDTO> toDtoList(List<Huissier> list);
}