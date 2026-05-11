package com.bna.gac.services;

import com.bna.gac.dto.MissionDTO;

import java.util.List;

public interface MissionService {

    MissionDTO create(MissionDTO dto);

    MissionDTO update(Long id, MissionDTO dto);

    MissionDTO getById(Long id);

    List<MissionDTO> getAll();

    void delete(Long id);

    List<MissionDTO> getByAffaireId(Long affaireId);

    MissionDTO validate(Long id);

    MissionDTO reject(Long id);
}

