package com.bna.gac.services.impl;

import com.bna.gac.dto.PermissionDTO;

import java.util.List;

public interface PermissionService {

    PermissionDTO createPermission(PermissionDTO dto);

    PermissionDTO updatePermission(Long id, PermissionDTO dto);

    void deletePermission(Long id);

    PermissionDTO getPermissionById(Long id);

    List<PermissionDTO> getAllPermissions();
}
