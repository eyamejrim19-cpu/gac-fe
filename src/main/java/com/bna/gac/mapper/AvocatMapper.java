package com.bna.gac.mapper;

import com.bna.gac.dto.AvocatDTO;
import com.bna.gac.entities.Avocat;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AvocatMapper {

    AvocatDTO toDto(Avocat entity);

    Avocat toEntity(AvocatDTO dto);

    List<AvocatDTO> toDtoList(List<Avocat> list);
}