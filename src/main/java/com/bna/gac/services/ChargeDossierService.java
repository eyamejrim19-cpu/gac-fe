package com.bna.gac.services;

import com.bna.gac.dto.ChargeDossierDTO;

import java.util.List;

public interface ChargeDossierService {

    ChargeDossierDTO create(ChargeDossierDTO dto);

    ChargeDossierDTO update(Long id, ChargeDossierDTO dto);

    List<ChargeDossierDTO> getAll();

    ChargeDossierDTO getById(Long id);

    void delete(Long id);
}

