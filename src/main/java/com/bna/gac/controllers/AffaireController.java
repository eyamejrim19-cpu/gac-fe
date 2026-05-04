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
    @PreAuthorize("hasAnyRole('CHARGE_DOSSIER', 'ADMIN')")
    public AffaireDTO create(@RequestBody AffaireDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('CHARGE_DOSSIER', 'ADMIN')")
    public AffaireDTO update(@PathVariable Long id, @RequestBody AffaireDTO dto) {
        return service.update(id, dto);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('CHARGE_DOSSIER', 'RESPONSABLE', 'ADMIN')")
    public List<AffaireDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/dossier/{dossierId}")
    @PreAuthorize("hasAnyRole('CHARGE_DOSSIER', 'RESPONSABLE', 'ADMIN')")
    public List<AffaireDTO> getByDossier(@PathVariable Long dossierId) {
        return service.getByDossierId(dossierId);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('CHARGE_DOSSIER', 'RESPONSABLE', 'ADMIN')")
    public AffaireDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('RESPONSABLE', 'ADMIN')")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}

