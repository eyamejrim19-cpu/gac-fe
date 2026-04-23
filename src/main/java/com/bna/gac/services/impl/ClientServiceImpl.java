package com.bna.gac.services.impl;

import com.bna.gac.dto.ClientDTO;
import com.bna.gac.entities.Client;
import com.bna.gac.repositories.ClientRepository;
import com.bna.gac.services.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository repository;

    @Override
    public ClientDTO save(ClientDTO dto) {
        Client client = new Client();

        client.setId(dto.getId());
        client.setNom(dto.getNom());
        client.setPrenom(dto.getPrenom());
        client.setCin(dto.getCin());
        client.setTel(dto.getTel());
        client.setEmail(dto.getEmail());
        client.setAdresse(dto.getAdresse());
        client.setActive(dto.getActive() != null ? dto.getActive() : true);

        client.setDateCreation(
                dto.getDateCreation() != null ? dto.getDateCreation() : LocalDateTime.now()
        );

        Client saved = repository.save(client);

        ClientDTO result = new ClientDTO();
        result.setId(saved.getId());
        result.setNom(saved.getNom());
        result.setPrenom(saved.getPrenom());
        result.setCin(saved.getCin());
        result.setTel(saved.getTel());
        result.setEmail(saved.getEmail());
        result.setAdresse(saved.getAdresse());
        result.setActive(saved.getActive());
        result.setDateCreation(saved.getDateCreation());

        return result;
    }

    @Override
    public List<ClientDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(c -> {
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
                })
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}