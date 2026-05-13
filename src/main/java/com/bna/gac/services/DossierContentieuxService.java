package com.bna.gac.services;

import com.bna.gac.dto.DossierContentieuxDTO;
import com.bna.gac.dto.DashboardStatsDTO;

import java.util.List;

public interface DossierContentieuxService {

    DossierContentieuxDTO create(DossierContentieuxDTO dto);

    DossierContentieuxDTO update(Long id, DossierContentieuxDTO dto);

    List<DossierContentieuxDTO> getAll();

    List<DossierContentieuxDTO> findRecent(int limit);

    DossierContentieuxDTO getById(Long id);

    void delete(Long id);

    DashboardStatsDTO getStats();

    DossierContentieuxDTO validate(Long id);

    DossierContentieuxDTO reject(Long id, String commentaireRejet);

    DossierContentieuxDTO requestClosure(Long id);

    DossierContentieuxDTO close(Long id);
}

