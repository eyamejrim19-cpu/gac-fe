package com.bna.gac.services.impl;


import com.bna.gac.dto.RisqueDTO;
import java.util.List;

public interface RisqueService {

    RisqueDTO save(RisqueDTO dto);

    List<RisqueDTO> findAll();

    RisqueDTO findById(Long id);

    RisqueDTO update(Long id, RisqueDTO dto);

    void delete(Long id);
}
