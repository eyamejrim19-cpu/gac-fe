package com.bna.gac.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.bna.gac.dto.RisqueDTO;
import com.bna.gac.services.RisqueService;

import java.util.List;

@RestController
@RequestMapping("/api/risques")
@RequiredArgsConstructor
public class RisqueController {

    private final RisqueService service;

    @PostMapping
    @PreAuthorize("hasAnyRole('CHARGE_DOSSIER', 'ADMIN')")
    public RisqueDTO create(@RequestBody RisqueDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('CHARGE_DOSSIER', 'ADMIN')")
    public RisqueDTO update(@PathVariable Long id, @RequestBody RisqueDTO dto) {
        return service.update(id, dto);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('CHARGE_DOSSIER', 'RESPONSABLE', 'ADMIN')")
    public List<RisqueDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('CHARGE_DOSSIER', 'RESPONSABLE', 'ADMIN')")
    public RisqueDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('RESPONSABLE', 'ADMIN')")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}

