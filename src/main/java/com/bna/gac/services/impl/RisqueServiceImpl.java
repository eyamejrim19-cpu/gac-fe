package com.bna.gac.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.bna.gac.dto.RisqueDTO;
import com.bna.gac.entities.Risque;
import com.bna.gac.mapper.RisqueMapper;
import com.bna.gac.repositories.RisqueRepository;
import com.bna.gac.services.RisqueService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RisqueServiceImpl implements RisqueService {

    private final RisqueRepository repository;
    private final RisqueMapper mapper;

    @Override
    public RisqueDTO save(RisqueDTO dto) {
        Risque risque = mapper.toEntity(dto);
        return mapper.toDTO(repository.save(risque));
    }

    @Override
    public List<RisqueDTO> findAll() {
        return mapper.toDTOList(repository.findAll());
    }
}