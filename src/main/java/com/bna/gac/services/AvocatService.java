package com.bna.gac.services;

import com.bna.gac.dto.AvocatDTO;

import java.util.List;

public interface AvocatService {

    AvocatDTO create(AvocatDTO dto);

    List<AvocatDTO> getAll();

    AvocatDTO getById(Long id);

    AvocatDTO update(Long id, AvocatDTO dto);

    void delete(Long id);
}

