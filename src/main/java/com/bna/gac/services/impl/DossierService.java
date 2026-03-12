package com.bna.gac.services;

import com.bna.gac.dto.DossierContentieuxDTO;
import java.util.List;

public interface DossierService {

    DossierContentieuxDTO save(DossierContentieuxDTO dto);

    List<DossierContentieuxDTO> findAll();
}