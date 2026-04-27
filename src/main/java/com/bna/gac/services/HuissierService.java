package com.bna.gac.services;

import com.bna.gac.dto.HuissierDTO;

import java.util.List;

public interface HuissierService {

    HuissierDTO create(HuissierDTO dto);

    List<HuissierDTO> getAll();

    HuissierDTO getById(Long id);

    HuissierDTO update(Long id, HuissierDTO dto);

    void delete(Long id);
}
