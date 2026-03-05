package com.bna.gac.services.impl;

import com.bna.gac.dto.ClientDTO;
import com.bna.gac.entities.Client;
import com.bna.gac.mapper.ClientMapper;
import com.bna.gac.repositories.ClientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
@RequiredArgsConstructor
@Transactional
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Override
    public ClientDTO createClient(ClientDTO dto) {
        Client client = clientMapper.toEntity(dto);
        client.setDateCreation(LocalDate.now().atStartOfDay());

        Client saved = clientRepository.save(client);
        return clientMapper.toDto(saved);
    }

    @Override
    public List<ClientDTO> getAllClients() {
        return clientMapper.toDtoList(clientRepository.findAll());
    }

    @Override
    public ClientDTO getClientById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        return clientMapper.toDto(client);
    }

    @Override
    public ClientDTO updateClient(Long id, ClientDTO dto) {

        Client existing = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        existing.setNom(dto.getNom());
        existing.setPrenom(dto.getPrenom());
        existing.setTel(dto.getTel());
        existing.setAdresse(dto.getAdresse());
        existing.setCin(dto.getCin());

        Client updated = clientRepository.save(existing);
        return clientMapper.toDto(updated);
    }

    @Override
    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }
}