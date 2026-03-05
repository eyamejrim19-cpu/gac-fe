package com.bna.gac.services.impl;

import com.bna.gac.dto.RoleDTO;
import com.bna.gac.entities.Permission;
import com.bna.gac.entities.Role;
import com.bna.gac.repositories.PermissionRepository;
import com.bna.gac.repositories.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Override
    public RoleDTO createRole(RoleDTO dto) {

        if (roleRepository.existsByName(dto.getName())) {
            throw new RuntimeException("Role already exists");
        }

        Role role = new Role();
        role.setName(dto.getName());
        role.setDescription(dto.getDescription());

        role = roleRepository.save(role);

        return mapToDTO(role);
    }

    @Override
    public RoleDTO updateRole(Long id, RoleDTO dto) {

        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        role.setName(dto.getName());
        role.setDescription(dto.getDescription());

        return mapToDTO(roleRepository.save(role));
    }

    @Override
    public void deleteRole(Long id) {

        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        roleRepository.delete(role);
    }

    @Override
    public RoleDTO getRoleById(Long id) {

        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        return mapToDTO(role);
    }

    @Override
    public List<RoleDTO> getAllRoles() {

        return roleRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public RoleDTO assignPermission(Long roleId, Long permissionId) {

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new RuntimeException("Permission not found"));

        role.getPermissions().add(permission);

        return mapToDTO(roleRepository.save(role));
    }

    @Override
    public RoleDTO removePermission(Long roleId, Long permissionId) {

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new RuntimeException("Permission not found"));

        role.getPermissions().remove(permission);

        return mapToDTO(roleRepository.save(role));
    }

    private RoleDTO mapToDTO(Role role) {

        RoleDTO dto = new RoleDTO();
        dto.setIdRole(role.getIdRole());
        dto.setName(role.getName());
        dto.setDescription(role.getDescription());

        Set<Long> permissionIds = role.getPermissions()
                .stream()
                .map(Permission::getIdPermission)
                .collect(Collectors.toSet());

        dto.setPermissionIds(permissionIds);

        return dto;
    }
}