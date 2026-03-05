package com.bna.gac.services.impl;


import com.bna.gac.dto.PrestataireDTO;
import java.util.List;

public interface PrestataireService {

    PrestataireDTO save(PrestataireDTO dto);

    List<PrestataireDTO> findAll();

    PrestataireDTO findById(Long id);

    PrestataireDTO update(Long id, PrestataireDTO dto);

    void delete(Long id);
}