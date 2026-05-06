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
        dto.setCin(c.getCin());
        dto.setRne(c.getRne());
        dto.setTel(c.getTel());
        dto.setEmail(c.getEmail());
        dto.setAdresse(c.getAdresse());
        dto.setTypeClient(c.getTypeClient());
        dto.setActive(c.getActive());
        dto.setDateCreation(c.getDateCreation());

        return dto;
    }

    public Client toEntity(ClientDTO dto) {

        if (dto == null) return null;

        Client c = new Client();

        c.setId(dto.getId());
        c.setNom(dto.getNom());
        c.setPrenom(dto.getPrenom());
        c.setTel(dto.getTel());
        c.setEmail(dto.getEmail());
        c.setAdresse(dto.getAdresse());
        c.setTypeClient(dto.getTypeClient());
        c.setActive(dto.getActive());

        // Only set the identifier that matches the type
        if ("MORALE".equalsIgnoreCase(dto.getTypeClient())) {
            c.setRne(dto.getRne());
        } else {
            c.setCin(dto.getCin());
        }

        return c;
    }
}


