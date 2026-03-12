package com.bna.gac.services;

import com.bna.gac.dto.DossierContentieuxDTO;
import java.util.List;

public interface DossierContentieuxService {

    DossierContentieuxDTO save(DossierContentieuxDTO dto);

    List<DossierContentieuxDTO> findAll();

    DossierContentieuxDTO create(DossierContentieuxDTO dto);

    List<DossierContentieuxDTO> getAll();

    DossierContentieuxDTO getById(Long id);

    void delete(Long id);
}