package com.bna.gac.services;

import com.bna.gac.dto.PermissionDTO;

import java.util.List;

public interface PermissionService {

    PermissionDTO create(PermissionDTO dto);

    PermissionDTO update(Long id, PermissionDTO dto);

    void delete(Long id);

    PermissionDTO getById(Long id);

    List<PermissionDTO> getAll();
}


