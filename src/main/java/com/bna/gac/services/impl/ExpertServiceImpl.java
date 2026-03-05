package com.bna.gac.services.impl;

import com.bna.gac.dto.ExpertDTO;
import com.bna.gac.entities.Expert;
import com.bna.gac.mapper.ExpertMapper;
import com.bna.gac.repositories.ExpertRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ExpertServiceImpl implements ExpertService {

    private final ExpertRepository repository;
    private final ExpertMapper mapper;

    @Override
    public ExpertDTO create(ExpertDTO dto) {
        Expert entity = mapper.toEntity(dto);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setEnabled(true);
        Expert saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    public List<ExpertDTO> getAll() {
        return mapper.toDtoList(repository.findAll());
    }

    @Override
    public ExpertDTO getById(Long id) {
        Expert entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expert not found"));
        return mapper.toDto(entity);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
