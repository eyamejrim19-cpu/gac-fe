package com.bna.gac.services.impl;

import com.bna.gac.dto.RoleDTO;
import com.bna.gac.entities.Permission;
import com.bna.gac.entities.Role;
import com.bna.gac.exceptions.ResourceConflictException;
import com.bna.gac.exceptions.ResourceNotFoundException;
import com.bna.gac.repositories.PermissionRepository;
import com.bna.gac.repositories.RoleRepository;
import com.bna.gac.services.RoleService;
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
    public RoleDTO create(RoleDTO dto) {
        if (roleRepository.existsByName(dto.getName())) {
            throw new ResourceConflictException("Role already exists");
        }

        Role role = new Role();
        role.setName(dto.getName());
        role.setDescription(dto.getDescription());
        return mapToDTO(roleRepository.save(role));
    }

    @Override
    public RoleDTO update(Long id, RoleDTO dto) {
        Role role = findRole(id);
        role.setName(dto.getName());
        role.setDescription(dto.getDescription());
        return mapToDTO(roleRepository.save(role));
    }

    @Override
    public void delete(Long id) {
        roleRepository.delete(findRole(id));
    }

    @Override
    public RoleDTO getById(Long id) {
        return mapToDTO(findRole(id));
    }

    @Override
    public List<RoleDTO> getAll() {
        return roleRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public RoleDTO assignPermission(Long roleId, Long permissionId) {
        Role role = findRole(roleId);
        Permission permission = findPermission(permissionId);
        role.getPermissions().add(permission);
        return mapToDTO(roleRepository.save(role));
    }

    @Override
    public RoleDTO removePermission(Long roleId, Long permissionId) {
        Role role = findRole(roleId);
        Permission permission = findPermission(permissionId);
        role.getPermissions().remove(permission);
        return mapToDTO(roleRepository.save(role));
    }

    private Role findRole(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
    }

    private Permission findPermission(Long id) {
        return permissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permission not found"));
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
