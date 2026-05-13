package com.bna.gac.services;

import com.bna.gac.dto.FactureDTO;
import java.util.List;

public interface FactureService {

    FactureDTO create(FactureDTO dto);

    FactureDTO update(Long id, FactureDTO dto);

    FactureDTO getById(Long id);

    List<FactureDTO> getAll();

    void delete(Long id);

    FactureDTO validate(Long id);

    FactureDTO reject(Long id, String commentaireRejet);

    FactureDTO pay(Long id);
}
