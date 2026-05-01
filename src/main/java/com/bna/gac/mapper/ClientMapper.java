package com.bna.gac.mapper;

import com.bna.gac.dto.ClientDTO;
import com.bna.gac.entities.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {

    public ClientDTO toDto(Client c) {
        if (c == null) return null;

        ClientDTO dto = new ClientDTO();
        dto.setId(c.getId());
        dto.setNom(c.getNom());
        dto.setPrenom(c.getPrenom());
        dto.setEmail(c.getEmail());
        dto.setTel(c.getTel());
        dto.setCin(c.getCin());
        dto.setRne(c.getRne());
        dto.setAdresse(c.getAdresse());
        dto.setTypeClient(c.getTypeClient());
        dto.setActive(c.getActive());
        dto.setDateCreation(c.getDateCreation());
        return dto;
    }

    public Client toEntity(ClientDTO dto) {
        if (dto == null) return null;

        Client c = new Client();

        c.setNom(dto.getNom());
        c.setPrenom(dto.getPrenom());
        c.setEmail(dto.getEmail());
        c.setTel(dto.getTel());
        c.setCin(dto.getCin());
        c.setRne(dto.getRne());
        c.setAdresse(dto.getAdresse());
        c.setTypeClient(dto.getTypeClient());
        c.setActive(dto.getActive() != null ? dto.getActive() : true);

        return c;
    }
}