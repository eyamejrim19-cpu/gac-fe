package com.bna.gac.services.impl;

import com.bna.gac.dto.HuissierDTO;
import com.bna.gac.entities.Huissier;
import com.bna.gac.exceptions.ResourceNotFoundException;
import com.bna.gac.mapper.HuissierMapper;
import com.bna.gac.repositories.HuissierRepository;
import com.bna.gac.services.HuissierService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class HuissierServiceImpl implements HuissierService {

    private final HuissierRepository repository;
    private final HuissierMapper mapper;

    @Override
    public HuissierDTO create(HuissierDTO dto) {
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    @Override
    public List<HuissierDTO> getAll() {
        return mapper.toDtoList(repository.findAll());
    }

    @Override
    public HuissierDTO getById(Long id) {
        return mapper.toDto(findHuissier(id));
    }

    @Override
    public HuissierDTO update(Long id, HuissierDTO dto) {
        Huissier existing = findHuissier(id);
        existing.setEmail(dto.getEmail());
        return mapper.toDto(repository.save(existing));
    }

    @Override
    public void delete(Long id) {
        repository.delete(findHuissier(id));
    }

    private Huissier findHuissier(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Huissier not found"));
    }
}

