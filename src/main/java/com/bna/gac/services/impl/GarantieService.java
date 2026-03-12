package com.bna.gac.services;

import com.bna.gac.dto.GarantieDTO;
import java.util.List;

public interface GarantieService {

    GarantieDTO save(GarantieDTO dto);

    List<GarantieDTO> findAll();
}