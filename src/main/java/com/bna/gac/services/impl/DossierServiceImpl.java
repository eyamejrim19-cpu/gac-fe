package com.bna.gac.services.impl;

import com.bna.gac.dto.DossierContentieuxDTO;
import com.bna.gac.entities.Client;
import com.bna.gac.entities.DossierContentieux;
import com.bna.gac.mapper.DossierContentieuxMapper;
import com.bna.gac.repositories.ClientRepository;
import com.bna.gac.repositories.DossierContentieuxRepository;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DossierServiceImpl implements DossierContentieuxService {

    private final DossierContentieuxRepository dossierRepository;
    private final ClientRepository clientRepository;
    private  DossierContentieuxMapper mapper;

    public DossierServiceImpl(DossierContentieuxRepository dossierRepository, ClientRepository clientRepository) {
        this.dossierRepository = dossierRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public DossierContentieuxDTO save(DossierContentieuxDTO dto) {

        DossierContentieux dossier = mapper.toEntity(dto);

        Client client = clientRepository.findById(dto.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));

        dossier.setClient(client);

        DossierContentieux saved = dossierRepository.save(dossier);

        return mapper.toDto(saved);
    }

    @Override
    public List<DossierContentieuxDTO> findAll() {
        return mapper.toDtoList(dossierRepository.findAll());
    }

    @Override
    public DossierContentieuxDTO create(DossierContentieuxDTO dto) {
        return null;
    }

    @Override
    public List<DossierContentieuxDTO> getAll() {
        return List.of();
    }

    @Override
    public DossierContentieuxDTO getById(Long id) {

        DossierContentieux dossier = dossierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dossier not found"));

        return mapper.toDto(dossier);
    }

    @Override
    public void delete(Long id) {
        dossierRepository.deleteById(id);
    }
}