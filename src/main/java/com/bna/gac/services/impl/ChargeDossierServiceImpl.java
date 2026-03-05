package com.bna.gac.services.impl;

import com.bna.gac.dto.ChargeDossierDTO;
import com.bna.gac.entities.ChargeDossier;
import com.bna.gac.mapper.ChargeDossierMapper;
import com.bna.gac.repositories.ChargeDossierRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ChargeDossierServiceImpl implements ChargeDossierService {

    private final ChargeDossierRepository repository;
    private final ChargeDossierMapper mapper;

    @Override
    public ChargeDossierDTO create(ChargeDossierDTO dto) {

        ChargeDossier entity = mapper.toEntity(dto);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setEnabled(true);

        ChargeDossier saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    public List<ChargeDossierDTO> getAll() {
        return mapper.toDtoList(repository.findAll());
    }

    @Override
    public ChargeDossierDTO getById(Long id) {
        ChargeDossier entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("ChargeDossier not found"));
        return mapper.toDto(entity);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
