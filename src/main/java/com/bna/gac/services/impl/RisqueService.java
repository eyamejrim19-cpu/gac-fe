package com.bna.gac.services;

import com.bna.gac.dto.RisqueDTO;
import java.util.List;

public interface RisqueService {

    RisqueDTO save(RisqueDTO dto);

    List<RisqueDTO> findAll();
}