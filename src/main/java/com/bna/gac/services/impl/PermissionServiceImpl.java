package com.bna.gac.services.impl;

import com.bna.gac.dto.PermissionDTO;
import com.bna.gac.entities.Permission;
import com.bna.gac.repositories.PermissionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;

    @Override
    public PermissionDTO createPermission(PermissionDTO dto) {

        if (permissionRepository.existsByCode(dto.getCode())) {
            throw new RuntimeException("Permission already exists");
        }

        Permission permission = new Permission();
        permission.setCode(dto.getCode());
        permission.setDescription(dto.getDescription());

        permission = permissionRepository.save(permission);

        return mapToDTO(permission);
    }

    @Override
    public PermissionDTO updatePermission(Long id, PermissionDTO dto) {

        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permission not found"));

        permission.setCode(dto.getCode());
        permission.setDescription(dto.getDescription());

        return mapToDTO(permissionRepository.save(permission));
    }

    @Override
    public void deletePermission(Long id) {

        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permission not found"));

        permissionRepository.delete(permission);
    }

    @Override
    public PermissionDTO getPermissionById(Long id) {

        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permission not found"));

        return mapToDTO(permission);
    }

    @Override
    public List<PermissionDTO> getAllPermissions() {

        return permissionRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    private PermissionDTO mapToDTO(Permission permission) {

        PermissionDTO dto = new PermissionDTO();
        dto.setIdPermission(permission.getIdPermission());
        dto.setCode(permission.getCode());
        dto.setDescription(permission.getDescription());

        return dto;
    }
}
