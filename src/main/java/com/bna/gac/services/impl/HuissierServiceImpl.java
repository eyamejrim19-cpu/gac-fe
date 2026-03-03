package com.bna.gac.services.impl;

import com.bna.gac.dto.HuissierDTO;
import com.bna.gac.entities.Huissier;
import com.bna.gac.mapper.HuissierMapper;
import com.bna.gac.repositories.HuissierRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class HuissierServiceImpl implements HuissierService {

    private final HuissierRepository repository;
    private final HuissierMapper mapper;

    @Override
    public HuissierDTO create(HuissierDTO dto) {

        Huissier entity = mapper.toEntity(dto);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setEnabled(true);

        Huissier saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    public List<HuissierDTO> getAll() {
        return mapper.toDtoList(repository.findAll());
    }

    @Override
    public HuissierDTO getById(Long id) {
        Huissier entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Huissier not found"));
        return mapper.toDto(entity);
    }

    @Override
    public HuissierDTO update(Long id, HuissierDTO dto) {

        Huissier existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Huissier not found"));

        existing.setCabinet(dto.getCabinet());
        existing.setUsername(dto.getUsername());
        existing.setEmail(dto.getEmail());

        Huissier updated = repository.save(existing);
        return mapper.toDto(updated);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}