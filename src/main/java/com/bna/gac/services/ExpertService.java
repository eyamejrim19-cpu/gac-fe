package com.bna.gac.services;

import com.bna.gac.dto.ExpertDTO;

import java.util.List;

public interface ExpertService {

    ExpertDTO create(ExpertDTO dto);

    ExpertDTO update(Long id, ExpertDTO dto);

    List<ExpertDTO> getAll();

    ExpertDTO getById(Long id);

    void delete(Long id);
}

