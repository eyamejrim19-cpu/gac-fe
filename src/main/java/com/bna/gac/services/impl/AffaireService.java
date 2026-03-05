package com.bna.gac.services.impl;

import com.bna.gac.dto.AffaireDTO;

import java.util.List;

public interface AffaireService {

    AffaireDTO create(AffaireDTO dto);

    List<AffaireDTO> getAll();

    AffaireDTO getById(Long id);

    void delete(Long id);
}