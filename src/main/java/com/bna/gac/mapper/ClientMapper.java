package com.bna.gac.mapper;

import com.bna.gac.dto.ClientDTO;
import com.bna.gac.entities.Client;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClientMapper {

    public ClientDTO toDTO(Client client) {
        if (client == null) return null;

        ClientDTO dto = new ClientDTO();
        dto.setIdClient(client.getIdClient());
        dto.setNom(client.getNom());
        dto.setPrenom(client.getPrenom());
        dto.setTel(client.getTel());
        dto.setAdresse(client.getAdresse());
        dto.setCin(client.getCin());
        dto.setDateCreation(client.getDateCreation());

        return dto;
    }

    public Client toEntity(ClientDTO dto) {
        if (dto == null) return null;

        Client client = new Client();
        client.setIdClient(dto.getIdClient());
        client.setNom(dto.getNom());
        client.setPrenom(dto.getPrenom());
        client.setTel(dto.getTel());
        client.setAdresse(dto.getAdresse());
        client.setCin(dto.getCin());
        client.setDateCreation(dto.getDateCreation());

        return client;
    }

    public List<ClientDTO> toDTOList(List<Client> clients) {
        return clients.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}