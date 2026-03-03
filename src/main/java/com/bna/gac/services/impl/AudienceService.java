package com.bna.gac.services.impl;

import com.bna.gac.dto.AudienceDTO;

import java.util.List;

public interface AudienceService {

    AudienceDTO create(AudienceDTO dto);

    List<AudienceDTO> getAll();

    AudienceDTO getById(Long id);

    void delete(Long id);
}
