package com.bna.gac.services;

import com.bna.gac.dto.AudienceDTO;

import java.util.List;

public interface AudienceService {

    AudienceDTO create(AudienceDTO dto);

    AudienceDTO update(Long id, AudienceDTO dto);

    List<AudienceDTO> getAll();

    AudienceDTO getById(Long id);

    void delete(Long id);
}

