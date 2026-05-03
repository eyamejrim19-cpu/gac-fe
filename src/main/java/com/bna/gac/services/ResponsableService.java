package com.bna.gac.services;

import com.bna.gac.dto.ResponsableDTO;
import java.util.List;

public interface ResponsableService {

    ResponsableDTO create(ResponsableDTO dto);

    List<ResponsableDTO> getAll();

    ResponsableDTO getById(Long id);

    ResponsableDTO update(Long id, ResponsableDTO dto);

    void delete(Long id);
}

