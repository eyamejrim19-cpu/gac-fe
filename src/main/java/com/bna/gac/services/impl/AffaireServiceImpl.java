package com.bna.gac.services.impl;

import com.bna.gac.dto.AffaireDTO;
import com.bna.gac.entities.Affaire;
import com.bna.gac.entities.Client;
import com.bna.gac.mapper.AffaireMapper;
import com.bna.gac.repositories.AffaireRepository;
import com.bna.gac.repositories.ClientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AffaireServiceImpl implements AffaireService {

    private final AffaireRepository affaireRepository;
    private final ClientRepository clientRepository;
    private final AffaireMapper mapper;

    @Override
    public AffaireDTO create(AffaireDTO dto) {

        Affaire affaire = mapper.toEntity(dto);

        Client client = clientRepository.findById(dto.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));

        affaire.setClient(client);
        affaire.setDateCreation(LocalDate.now());

        Affaire saved = affaireRepository.save(affaire);

        return mapper.toDto(saved);
    }

    @Override
    public List<AffaireDTO> getAll() {
        return mapper.toDtoList(affaireRepository.findAll());
    }

    @Override
    public AffaireDTO getById(Long id) {
        Affaire affaire = affaireRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Affaire not found"));
        return mapper.toDto(affaire);
    }

    @Override
    public void delete(Long id) {
        affaireRepository.deleteById(id);
    }
}