package com.bna.gac.services.impl;

import com.bna.gac.dto.AvocatDTO;
import com.bna.gac.entities.Avocat;
import com.bna.gac.exceptions.ResourceNotFoundException;
import com.bna.gac.mapper.AvocatMapper;
import com.bna.gac.repositories.AvocatRepository;
import com.bna.gac.services.AvocatService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AvocatServiceImpl implements AvocatService {

    private final AvocatRepository repository;
    private final AvocatMapper mapper;

    @Override
    public AvocatDTO create(AvocatDTO dto) {
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    @Override
    public List<AvocatDTO> getAll() {
        return mapper.toDtoList(repository.findAll());
    }

    @Override
    public AvocatDTO getById(Long id) {
        return mapper.toDto(findAvocat(id));
    }

    @Override
    public AvocatDTO update(Long id, AvocatDTO dto) {
        Avocat existing = findAvocat(id);
        existing.setBarreau(dto.getBarreau());
        existing.setEmail(dto.getEmail());
        return mapper.toDto(repository.save(existing));
    }

    @Override
    public void delete(Long id) {
        repository.delete(findAvocat(id));
    }

    private Avocat findAvocat(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Avocat not found"));
    }
}

