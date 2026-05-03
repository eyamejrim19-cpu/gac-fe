package com.bna.gac.services;

import com.bna.gac.dto.RoleDTO;

import java.util.List;

public interface RoleService {

    RoleDTO create(RoleDTO dto);

    RoleDTO update(Long id, RoleDTO dto);

    void delete(Long id);

    RoleDTO getById(Long id);

    List<RoleDTO> getAll();

    RoleDTO assignPermission(Long roleId, Long permissionId);

    RoleDTO removePermission(Long roleId, Long permissionId);
}

