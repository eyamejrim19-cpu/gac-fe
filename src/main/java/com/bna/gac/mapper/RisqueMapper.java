package com.bna.gac.mapper;


import com.bna.gac.dto.RisqueDTO;
import com.bna.gac.entities.Risque;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RisqueMapper {

    @Mapping(source = "dossier.idDossier", target = "dossierId")
    RisqueDTO toDTO(Risque risque);

    @Mapping(source = "dossierId", target = "dossier.idDossier")
    Risque toEntity(RisqueDTO dto);

    List<RisqueDTO> toDTOList(List<Risque> all);
}
