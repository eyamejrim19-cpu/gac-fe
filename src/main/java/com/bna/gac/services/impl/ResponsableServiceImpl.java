package com.bna.gac.services.impl;

import com.bna.gac.dto.ResponsableDTO;
import com.bna.gac.entities.Responsable;
import com.bna.gac.exceptions.ResourceNotFoundException;
import com.bna.gac.mapper.ResponsableMapper;
import com.bna.gac.repositories.ResponsableRepository;
import com.bna.gac.services.ResponsableService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResponsableServiceImpl implements ResponsableService {

    private final ResponsableRepository repository;
    private final ResponsableMapper mapper;

    @Override
    public ResponsableDTO create(ResponsableDTO dto) {
        Responsable entity = mapper.toEntity(dto);
        return mapper.toDTO(repository.save(entity));
    }

    @Override
    public List<ResponsableDTO> getAll() {
        return mapper.toDTOList(repository.findAll());
    }

    @Override
    public ResponsableDTO getById(Long id) {
        return mapper.toDTO(findResponsable(id));
    }

    @Override
    public ResponsableDTO update(Long id, ResponsableDTO dto) {
        Responsable existing = findResponsable(id);
        existing.setNom(dto.getNom());
        existing.setPrenom(dto.getPrenom());
        existing.setEmail(dto.getEmail());
        existing.setTelephone(dto.getTelephone());
        return mapper.toDTO(repository.save(existing));
    }

    @Override
    public void delete(Long id) {
        repository.delete(findResponsable(id));
    }

    private Responsable findResponsable(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Responsable not found"));
    }
}

