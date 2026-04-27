package com.bna.gac.services;

import com.bna.gac.dto.AffaireDTO;

import java.util.List;

public interface AffaireService {

    AffaireDTO create(AffaireDTO dto);

    AffaireDTO update(Long id, AffaireDTO dto);

    List<AffaireDTO> getAll();

    AffaireDTO getById(Long id);

    void delete(Long id);
}
