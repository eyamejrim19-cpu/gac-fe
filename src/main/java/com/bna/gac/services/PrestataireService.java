package com.bna.gac.services;

import com.bna.gac.entities.TypePrestataire;
import com.bna.gac.dto.PrestataireDTO;

import java.util.List;

public interface PrestataireService {

    PrestataireDTO create(PrestataireDTO prestataire);

    List<PrestataireDTO> getAll();

    PrestataireDTO getById(Long id);

    List<PrestataireDTO> getByType(TypePrestataire type);

    PrestataireDTO update(Long id, PrestataireDTO prestataire);

    PrestataireDTO updateStatus(Long id, boolean actif);

    void delete(Long id);

}

