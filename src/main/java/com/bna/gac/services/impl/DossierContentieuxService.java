package com.bna.gac.services.impl;

import com.bna.gac.dto.DossierContentieuxDTO;

import java.util.List;

public interface DossierContentieuxService {

    DossierContentieuxDTO createDossier(DossierContentieuxDTO dto);

    DossierContentieuxDTO updateDossier(Long id, DossierContentieuxDTO dto);

    void deleteDossier(Long id);

    DossierContentieuxDTO getDossierById(Long id);

    List<DossierContentieuxDTO> getAllDossiers();

    List<DossierContentieuxDTO> getDossiersByStatut(String statut);

    DossierContentieuxDTO closeDossier(Long id);
}
