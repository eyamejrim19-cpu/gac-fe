package com.bna.gac.services.impl;

import com.bna.gac.dto.ClientDTO;
import com.bna.gac.entities.Client;
import com.bna.gac.exceptions.ResourceNotFoundException;
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
    public ClientDTO create(ClientDTO dto) {
        Client client = new Client();
        client.setNom(dto.getNom());
        client.setPrenom(dto.getPrenom());
        client.setCin(dto.getCin());
        client.setTel(dto.getTel());
        client.setEmail(dto.getEmail());
        client.setAdresse(dto.getAdresse());
        client.setActive(dto.getActive() != null ? dto.getActive() : true);
        client.setDateCreation(dto.getDateCreation() != null ? dto.getDateCreation() : LocalDateTime.now());
        return toDto(repository.save(client));
    }

    @Override
    public ClientDTO update(Long id, ClientDTO dto) {
        Client client = findClient(id);
        client.setNom(dto.getNom());
        client.setPrenom(dto.getPrenom());
        client.setCin(dto.getCin());
        client.setTel(dto.getTel());
        client.setEmail(dto.getEmail());
        client.setAdresse(dto.getAdresse());
        if (dto.getActive() != null) {
            client.setActive(dto.getActive());
        }
        return toDto(repository.save(client));
    }

    @Override
    public ClientDTO getById(Long id) {
        return toDto(findClient(id));
    }

    @Override
    public List<ClientDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        repository.delete(findClient(id));
    }

    private Client findClient(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));
    }

    private ClientDTO toDto(Client client) {
        ClientDTO dto = new ClientDTO();
        dto.setId(client.getId());
        dto.setNom(client.getNom());
        dto.setPrenom(client.getPrenom());
        dto.setCin(client.getCin());
        dto.setTel(client.getTel());
        dto.setEmail(client.getEmail());
        dto.setAdresse(client.getAdresse());
        dto.setActive(client.getActive());
        dto.setDateCreation(client.getDateCreation());
        return dto;
    }
}
