package com.bna.gac.services.impl;

import com.bna.gac.dto.FactureDto;

import java.util.List;

public interface FactureService {

    FactureDto create(FactureDto dto);

    FactureDto update(Long id, FactureDto dto);

    FactureDto getById(Long id);

    List<FactureDto> getAll();

    void delete(Long id);
}
