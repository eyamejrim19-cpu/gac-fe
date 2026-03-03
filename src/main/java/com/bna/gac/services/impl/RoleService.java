package com.bna.gac.services.impl;

import com.bna.gac.dto.RoleDTO;

import java.util.List;

public interface RoleService {

    RoleDTO createRole(RoleDTO dto);

    RoleDTO updateRole(Long id, RoleDTO dto);

    void deleteRole(Long id);

    RoleDTO getRoleById(Long id);

    List<RoleDTO> getAllRoles();

    RoleDTO assignPermission(Long roleId, Long permissionId);

    RoleDTO removePermission(Long roleId, Long permissionId);
}