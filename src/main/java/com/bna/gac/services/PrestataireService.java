package com.bna.gac.services;

import com.bna.gac.dto.PrestataireDTO;
import com.bna.gac.entities.TypePrestataire;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PrestataireService {

    PrestataireDTO create(PrestataireDTO dto);

    List<PrestataireDTO> getAll();

    PrestataireDTO getById(Long id);

    List<PrestataireDTO> getByType(TypePrestataire type);

    Page<PrestataireDTO> getPaginated(int page, int size, String search,
                                      TypePrestataire type, Boolean actif);

    PrestataireDTO update(Long id, PrestataireDTO dto);

    PrestataireDTO updateStatus(Long id, boolean actif);

    void delete(Long id);
}
