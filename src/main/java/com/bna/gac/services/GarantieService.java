package com.bna.gac.services;

import com.bna.gac.dto.GarantieDTO;
import java.util.List;

public interface GarantieService {

    GarantieDTO create(GarantieDTO dto);

    GarantieDTO update(Long id, GarantieDTO dto);

    GarantieDTO getById(Long id);

    List<GarantieDTO> getAll();

    void delete(Long id);
}

