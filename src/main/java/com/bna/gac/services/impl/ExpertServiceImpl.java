package com.bna.gac.services.impl;

import com.bna.gac.dto.ExpertDTO;
import com.bna.gac.entities.Expert;
import com.bna.gac.exceptions.ResourceNotFoundException;
import com.bna.gac.mapper.ExpertMapper;
import com.bna.gac.repositories.ExpertRepository;
import com.bna.gac.services.ExpertService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ExpertServiceImpl implements ExpertService {

    private final ExpertRepository repository;
    private final ExpertMapper mapper;

    @Override
    public ExpertDTO create(ExpertDTO dto) {
        Expert entity = mapper.toEntity(dto);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    public ExpertDTO update(Long id, ExpertDTO dto) {
        Expert entity = findExpert(id);
        entity.setEmail(dto.getEmail());
        entity.setDomaineExpertise(dto.getSpecialite());
        return mapper.toDto(repository.save(entity));
    }

    @Override
    public List<ExpertDTO> getAll() {
        return mapper.toDtoList(repository.findAll());
    }

    @Override
    public ExpertDTO getById(Long id) {
        return mapper.toDto(findExpert(id));
    }

    @Override
    public void delete(Long id) {
        repository.delete(findExpert(id));
    }

    private Expert findExpert(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expert not found"));
    }
}
