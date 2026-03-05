package com.bna.gac.services.impl;

import com.bna.gac.dto.ChargeDossierDTO;

import java.util.List;

public interface ChargeDossierService {

    ChargeDossierDTO create(ChargeDossierDTO dto);

    List<ChargeDossierDTO> getAll();

    ChargeDossierDTO getById(Long id);

    void delete(Long id);
}
