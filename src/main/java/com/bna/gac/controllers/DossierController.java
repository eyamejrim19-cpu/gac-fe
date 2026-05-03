package com.bna.gac.controllers;

import com.bna.gac.services.DossierContentieuxService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.bna.gac.dto.DossierContentieuxDTO;

import java.util.List;

@RestController
@RequestMapping("/api/dossiers")
@RequiredArgsConstructor
public class DossierController {

    private final DossierContentieuxService service;

    @GetMapping("/recent")
    @PreAuthorize("hasAnyRole('CHARGE_DOSSIER', 'RESPONSABLE', 'ADMIN')")
    public List<DossierContentieuxDTO> getRecent(@RequestParam int limit) {
        return service.findRecent(limit);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('CHARGE_DOSSIER', 'ADMIN')")
    public DossierContentieuxDTO create(@RequestBody DossierContentieuxDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('CHARGE_DOSSIER', 'ADMIN')")
    public DossierContentieuxDTO update(@PathVariable Long id, @RequestBody DossierContentieuxDTO dto) {
        return service.update(id, dto);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('CHARGE_DOSSIER', 'RESPONSABLE', 'ADMIN')")
    public List<DossierContentieuxDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('CHARGE_DOSSIER', 'RESPONSABLE', 'ADMIN')")
    public DossierContentieuxDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('RESPONSABLE', 'ADMIN')")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}

