package com.bna.gac.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.bna.gac.dto.ClientDTO;
import com.bna.gac.entities.Client;
import com.bna.gac.mapper.ClientMapper;
import com.bna.gac.repositories.ClientRepository;
import com.bna.gac.services.ClientService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository repository;
    private final ClientMapper mapper;

    @Override
    public ClientDTO save(ClientDTO dto) {
        Client client = mapper.toEntity(dto);
        return mapper.toDTO(repository.save(client));
    }

    @Override
    public List<ClientDTO> findAll() {
        return mapper.toDTOList(repository.findAll());
    }

    @Override
    public ClientDTO findById(Long id) {
        Client client = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        return mapper.toDTO(client);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}