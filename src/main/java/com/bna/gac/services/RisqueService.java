package com.bna.gac.services;

import com.bna.gac.dto.RisqueDTO;
import java.util.List;

public interface RisqueService {

    RisqueDTO create(RisqueDTO dto);

    RisqueDTO update(Long id, RisqueDTO dto);

    RisqueDTO getById(Long id);

    List<RisqueDTO> getAll();

    void delete(Long id);
}

