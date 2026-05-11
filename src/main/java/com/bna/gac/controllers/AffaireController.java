package com.bna.gac.controllers;

import com.bna.gac.dto.AffaireDTO;
import com.bna.gac.services.AffaireService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/affaires")
@RequiredArgsConstructor
public class AffaireController {

    private final AffaireService service;

    @PostMapping
    @PreAuthorize("hasAnyRole('CHARGEDOSSIER')")
    public AffaireDTO create(@RequestBody AffaireDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('CHARGEDOSSIER')")
    public AffaireDTO update(@PathVariable Long id, @RequestBody AffaireDTO dto) {
        return service.update(id, dto);
    }

    // Responsable validates an affaire (sets status to TERMINEE)
    @PutMapping("/{id}/validate")
    @PreAuthorize("hasAnyRole('RESPONSABLE')")
    public AffaireDTO validateAffaire(@PathVariable Long id) {
        return service.validate(id);
    }

    // Responsable rejects an affaire (sets status back to EN_COURS)
    @PutMapping("/{id}/reject")
    @PreAuthorize("hasAnyRole('RESPONSABLE')")
    public AffaireDTO rejectAffaire(@PathVariable Long id) {
        return service.reject(id);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('CHARGEDOSSIER', 'RESPONSABLE', 'ADMIN')")
    public List<AffaireDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/dossier/{dossierId}")
    @PreAuthorize("hasAnyRole('CHARGEDOSSIER', 'RESPONSABLE', 'ADMIN')")
    public List<AffaireDTO> getByDossier(@PathVariable Long dossierId) {
        return service.getByDossierId(dossierId);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('CHARGEDOSSIER', 'RESPONSABLE', 'ADMIN')")
    public AffaireDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('CHARGEDOSSIER')")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}

