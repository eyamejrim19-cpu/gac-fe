package com.bna.gac.services.impl;

import com.bna.gac.dto.PermissionDTO;
import com.bna.gac.entities.Permission;
import com.bna.gac.exceptions.ResourceConflictException;
import com.bna.gac.exceptions.ResourceNotFoundException;
import com.bna.gac.repositories.PermissionRepository;
import com.bna.gac.services.PermissionService;
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
    public PermissionDTO create(PermissionDTO dto) {
        if (permissionRepository.existsByCode(dto.getCode())) {
            throw new ResourceConflictException("Permission already exists");
        }

        Permission permission = new Permission();
        permission.setCode(dto.getCode());
        permission.setDescription(dto.getDescription());

        return mapToDTO(permissionRepository.save(permission));
    }

    @Override
    public PermissionDTO update(Long id, PermissionDTO dto) {
        Permission permission = findPermission(id);
        permission.setCode(dto.getCode());
        permission.setDescription(dto.getDescription());
        return mapToDTO(permissionRepository.save(permission));
    }

    @Override
    public void delete(Long id) {
        permissionRepository.delete(findPermission(id));
    }

    @Override
    public PermissionDTO getById(Long id) {
        return mapToDTO(findPermission(id));
    }

    @Override
    public List<PermissionDTO> getAll() {
        return permissionRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    private Permission findPermission(Long id) {
        return permissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permission not found"));
    }

    private PermissionDTO mapToDTO(Permission permission) {
        PermissionDTO dto = new PermissionDTO();
        dto.setIdPermission(permission.getIdPermission());
        dto.setCode(permission.getCode());
        dto.setDescription(permission.getDescription());
        return dto;
    }
}
