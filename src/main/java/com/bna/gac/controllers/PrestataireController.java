package com.bna.gac.controllers;

import com.bna.gac.entities.TypePrestataire;
import com.bna.gac.dto.PrestataireDTO;
import com.bna.gac.services.PrestataireService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prestataires")
@RequiredArgsConstructor
public class PrestataireController {

    private final PrestataireService prestataireService;

    @PostMapping
    @PreAuthorize("hasAnyRole('RESPONSABLE', 'ADMIN')")
    public PrestataireDTO create(@RequestBody PrestataireDTO prestataire) {
        return prestataireService.create(prestataire);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('CHARGEDOSSIER', 'RESPONSABLE', 'ADMIN')")
    public List<PrestataireDTO> getAll() {
        return prestataireService.getAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('CHARGEDOSSIER', 'RESPONSABLE', 'ADMIN')")
    public PrestataireDTO getById(@PathVariable Long id) {
        return prestataireService.getById(id);
    }

    @GetMapping("/type/{type}")
    @PreAuthorize("hasAnyRole('CHARGEDOSSIER', 'RESPONSABLE', 'ADMIN')")
    public List<PrestataireDTO> getByType(@PathVariable String type) {
        return prestataireService.getByType(TypePrestataire.valueOf(type));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('RESPONSABLE', 'ADMIN')")
    public PrestataireDTO update(@PathVariable Long id, @RequestBody PrestataireDTO prestataire) {
        return prestataireService.update(id, prestataire);
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('RESPONSABLE', 'ADMIN')")
    public PrestataireDTO updateStatus(@PathVariable Long id, @RequestParam boolean actif) {
        return prestataireService.updateStatus(id, actif);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('RESPONSABLE', 'ADMIN')")
    public void delete(@PathVariable Long id) {
        prestataireService.delete(id);
    }
}
