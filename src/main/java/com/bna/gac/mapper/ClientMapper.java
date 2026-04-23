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
        dto.setTel(c.getTel());
        dto.setEmail(c.getEmail());
        dto.setAdresse(c.getAdresse());
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
        c.setCin(dto.getCin());
        c.setTel(dto.getTel());
        c.setEmail(dto.getEmail());
        c.setAdresse(dto.getAdresse());
        c.setActive(dto.getActive());

        return c;
    }
}

