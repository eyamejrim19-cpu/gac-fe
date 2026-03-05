package com.bna.gac.mapper;

import com.bna.gac.dto.ExpertDTO;
import com.bna.gac.entities.Expert;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExpertMapper {

    ExpertDTO toDto(Expert entity);

    Expert toEntity(ExpertDTO dto);

    List<ExpertDTO> toDtoList(List<Expert> list);
}