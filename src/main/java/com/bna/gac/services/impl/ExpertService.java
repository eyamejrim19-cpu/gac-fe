package com.bna.gac.services.impl;

import com.bna.gac.dto.ExpertDTO;

import java.util.List;

public interface ExpertService {

    ExpertDTO create(ExpertDTO dto);

    List<ExpertDTO> getAll();

    ExpertDTO getById(Long id);

    void delete(Long id);
}
