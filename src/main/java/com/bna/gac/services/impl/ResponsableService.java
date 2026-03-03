package com.bna.gac.services.impl;

import com.bna.gac.dto.ResponsableDTO;
import java.util.List;

public interface ResponsableService {

    ResponsableDTO save(ResponsableDTO dto);

    List<ResponsableDTO> findAll();

    ResponsableDTO findById(Long id);

    ResponsableDTO update(Long id, ResponsableDTO dto);

    void delete(Long id);
}