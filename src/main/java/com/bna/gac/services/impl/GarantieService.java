package com.bna.gac.services.impl;

import com.bna.gac.dto.GarantieDto;

import java.util.List;

public interface GarantieService {

    GarantieDto create(GarantieDto dto);

    GarantieDto update(Long id, GarantieDto dto);

    GarantieDto getById(Long id);

    List<GarantieDto> getAll();

    void delete(Long id);
}
