package com.bna.gac.services.impl;

import com.bna.gac.dto.ChargeDossierDTO;
import com.bna.gac.entities.ChargeDossier;
import com.bna.gac.exceptions.ResourceNotFoundException;
import com.bna.gac.mapper.ChargeDossierMapper;
import com.bna.gac.repositories.ChargeDossierRepository;
import com.bna.gac.services.ChargeDossierService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ChargeDossierServiceImpl implements ChargeDossierService {

    private final ChargeDossierRepository repository;
    private final ChargeDossierMapper mapper;

    @Override
    public ChargeDossierDTO create(ChargeDossierDTO dto) {
        ChargeDossier entity = mapper.toEntity(dto);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    public ChargeDossierDTO update(Long id, ChargeDossierDTO dto) {
        ChargeDossier entity = findChargeDossier(id);
        entity.setUsername(dto.getUsername());
        entity.setEmail(dto.getEmail());
        entity.setTelephone(dto.getTelephone());
        return mapper.toDto(repository.save(entity));
    }

    @Override
    public List<ChargeDossierDTO> getAll() {
        return mapper.toDtoList(repository.findAll());
    }

    @Override
    public ChargeDossierDTO getById(Long id) {
        return mapper.toDto(findChargeDossier(id));
    }

    @Override
    public void delete(Long id) {
        repository.delete(findChargeDossier(id));
    }

    private ChargeDossier findChargeDossier(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Charge dossier not found"));
    }
}
