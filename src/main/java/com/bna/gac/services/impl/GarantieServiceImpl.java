package com.bna.gac.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.bna.gac.dto.GarantieDTO;
import com.bna.gac.entities.Garantie;
import com.bna.gac.mapper.GarantieMapper;
import com.bna.gac.repositories.GarantieRepository;
import com.bna.gac.services.GarantieService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GarantieServiceImpl implements GarantieService {

    private final GarantieRepository repository;
    private final GarantieMapper mapper;

    @Override
    public GarantieDTO save(GarantieDTO dto) {
        Garantie garantie = mapper.toEntity(dto);
        return mapper.toDTO(repository.save(garantie));
    }

    @Override
    public List<GarantieDTO> findAll() {
        return mapper.toDTOList(repository.findAll());
    }
}