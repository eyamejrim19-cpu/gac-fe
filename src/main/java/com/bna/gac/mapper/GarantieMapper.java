package com.bna.gac.mapper;

import com.bna.gac.dto.GarantieDto;
import com.bna.gac.entities.Garantie;
import com.bna.gac.entities.Risque;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GarantieMapper {

    @Mapping(target = "risqueIds", expression = "java(mapRisqueIds(garantie.getRisques()))")
    GarantieDto toDto(Garantie garantie);

    @Mapping(target = "risques", ignore = true)
    Garantie toEntity(GarantieDto dto);

    List<GarantieDto> toDtoList(List<Garantie> garanties);

    default List<Long> mapRisqueIds(List<Risque> risques) {
        if (risques == null) return null;
        return risques.stream()
                .map(Risque::getIdRisque)
                .toList();
    }
}
