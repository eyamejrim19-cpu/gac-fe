package com.bna.gac.services.impl;

import com.bna.gac.dto.AvocatDTO;

import java.util.List;

public interface AvocatService {

    AvocatDTO create(AvocatDTO dto);

    List<AvocatDTO> getAll();

    AvocatDTO getById(Long id) throws Throwable;

    AvocatDTO update(Long id, AvocatDTO dto) throws Throwable;

    void delete(Long id);
}