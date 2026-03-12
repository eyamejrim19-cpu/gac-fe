package com.bna.gac.services.impl;

import com.bna.gac.dto.DossierContentieuxDTO;
import com.bna.gac.entities.DossierContentieux;
import com.bna.gac.mapper.DossierContentieuxMapper;
import com.bna.gac.repositories.DossierContentieuxRepository;
import com.bna.gac.services.DossierContentieuxService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public abstract class DossierContentieuxServiceImpl implements DossierContentieuxService {

    private final DossierContentieuxRepository repository;
    private final DossierContentieuxMapper mapper;

    @Override
    public DossierContentieuxDTO create(DossierContentieuxDTO dto) {
        DossierContentieux dossier = mapper.toEntity(dto);
        return mapper.toDTO(repository.save(dossier));
    }

    @Override
    public List<DossierContentieuxDTO> getAll() {
        return mapper.toDTOList(repository.findAll());
    }

    @Override
    public DossierContentieuxDTO getById(Long id) {
        DossierContentieux d = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dossier not found"));
        return mapper.toDTO(d);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}