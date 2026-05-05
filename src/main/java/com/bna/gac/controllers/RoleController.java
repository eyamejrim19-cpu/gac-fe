package com.bna.gac.controllers;

import com.bna.gac.dto.RoleDTO;
import com.bna.gac.services.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoleDTO> create(@Valid @RequestBody RoleDTO dto) {
        RoleDTO response = roleService.create(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<RoleDTO>> getAll() {
        List<RoleDTO> response = roleService.getAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoleDTO> getById(@PathVariable Long id) {
        RoleDTO response = roleService.getById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoleDTO> update(@PathVariable Long id, @Valid @RequestBody RoleDTO dto) {
        RoleDTO response = roleService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        roleService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{roleId}/permissions/{permissionId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoleDTO> assignPermission(@PathVariable Long roleId, @PathVariable Long permissionId) {
        return ResponseEntity.ok(roleService.assignPermission(roleId, permissionId));
    }

    @DeleteMapping("/{roleId}/permissions/{permissionId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoleDTO> removePermission(@PathVariable Long roleId, @PathVariable Long permissionId) {
        return ResponseEntity.ok(roleService.removePermission(roleId, permissionId));
    }
}
