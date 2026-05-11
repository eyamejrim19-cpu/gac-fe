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
    @PreAuthorize("hasAnyRole('CHARGEDOSSIER', 'RESPONSABLE', 'ADMIN')")
    public List<DossierContentieuxDTO> getRecent(@RequestParam int limit) {
        return service.findRecent(limit);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('CHARGEDOSSIER')")
    public DossierContentieuxDTO create(@RequestBody DossierContentieuxDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('CHARGEDOSSIER')")
    public DossierContentieuxDTO update(@PathVariable Long id, @RequestBody DossierContentieuxDTO dto) {
        return service.update(id, dto);
    }

    // Responsable validates a dossier (sets status to VALIDE)
    @PutMapping("/{id}/validate")
    @PreAuthorize("hasAnyRole('RESPONSABLE')")
    public DossierContentieuxDTO validateDossier(@PathVariable Long id) {
        return service.validate(id);
    }

    // Responsable rejects a dossier (sets status back to EN_COURS)
    @PutMapping("/{id}/reject")
    @PreAuthorize("hasAnyRole('RESPONSABLE')")
    public DossierContentieuxDTO rejectDossier(@PathVariable Long id) {
        return service.reject(id);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('CHARGEDOSSIER', 'RESPONSABLE', 'ADMIN')")
    public List<DossierContentieuxDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('CHARGEDOSSIER', 'RESPONSABLE', 'ADMIN')")
    public DossierContentieuxDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('CHARGEDOSSIER')")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}

