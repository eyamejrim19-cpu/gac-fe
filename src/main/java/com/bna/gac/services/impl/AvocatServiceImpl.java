package com.bna.gac.services.impl;

import com.bna.gac.dto.AvocatDTO;
import com.bna.gac.entities.Avocat;
import com.bna.gac.mapper.AvocatMapper;
import com.bna.gac.repositories.AvocatRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class AvocatServiceImpl implements AvocatService {

    private final AvocatRepository repository;
    private  AvocatMapper mapper;

    public AvocatServiceImpl(AvocatRepository repository) {
        this.repository = repository;
    }

    @Override
    public AvocatDTO create(AvocatDTO dto) {

        Avocat entity = mapper.toEntity(dto);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setEnabled(true);

        Avocat saved = (Avocat) repository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    public List<AvocatDTO> getAll() {
        return mapper.toDtoList(repository.findAll());
    }

    @Override
    public AvocatDTO getById(Long id) throws Throwable {
        Avocat entity = (Avocat) repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Avocat not found"));
        return mapper.toDto(entity);
    }

    @Override
    public AvocatDTO update(Long id, AvocatDTO dto) throws Throwable {

        Avocat existing = (Avocat) repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Avocat not found"));

        existing.setBarreau(dto.getBarreau());
        existing.setDomaine(dto.getDomaine());
        existing.setUsername(dto.getUsername());
        existing.setEmail(dto.getEmail());

        Avocat updated = (Avocat) repository.save(existing);
        return mapper.toDto(updated);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
