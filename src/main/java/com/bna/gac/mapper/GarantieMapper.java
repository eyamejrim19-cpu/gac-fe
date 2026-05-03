package com.bna.gac.mapper;

import com.bna.gac.dto.GarantieDTO;
import com.bna.gac.entities.Garantie;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GarantieMapper {

    public GarantieDTO toDTO(Garantie g) {

        GarantieDTO dto = new GarantieDTO();
        dto.setIdGarantie(g.getIdGarantie());
        dto.setTypeGarantie(g.getTypeGarantie());
        dto.setDescription(g.getDescription());
        dto.setValeur(g.getValeur());
        dto.setStatut(g.getStatut());

        if (g.getRisque() != null) {
            dto.setRisqueId(g.getRisque().getIdRisque());
        }

        return dto;
    }

    public Garantie toEntity(GarantieDTO dto) {

        Garantie g = new Garantie();
        g.setIdGarantie(dto.getIdGarantie());
        g.setTypeGarantie(dto.getTypeGarantie());
        g.setDescription(dto.getDescription());
        g.setValeur(dto.getValeur());
        g.setStatut(dto.getStatut());

        return g;
    }

    public List<GarantieDTO> toDTOList(List<Garantie> list) {
        return list.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
