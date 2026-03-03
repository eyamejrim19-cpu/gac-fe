package com.bna.gac.mapper;

import com.bna.gac.dto.AffaireDTO;
import com.bna.gac.entities.Affaire;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AffaireMapper {

    @Mapping(source = "client.idClient", target = "clientId")
    AffaireDTO toDto(Affaire entity);

    @Mapping(source = "clientId", target = "client.idClient")
    Affaire toEntity(AffaireDTO dto);

    List<AffaireDTO> toDtoList(List<Affaire> list);
}
