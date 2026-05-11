package com.bna.gac.controllers;

import com.bna.gac.dto.FactureDTO;
import com.bna.gac.services.FactureService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/factures")
@RequiredArgsConstructor
public class FactureController {

    private final FactureService factureService;

    @GetMapping
    @PreAuthorize("hasAnyRole('CHARGEDOSSIER', 'RESPONSABLE', 'ADMIN')")
    public List<FactureDTO> getAllFactures() {
        return factureService.getAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('CHARGEDOSSIER', 'RESPONSABLE', 'ADMIN')")
    public FactureDTO getFactureById(@PathVariable Long id) {
        return factureService.getById(id);
    }

    // Only ChargeDossier creates factures
    @PostMapping
    @PreAuthorize("hasAnyRole('CHARGEDOSSIER')")
    public FactureDTO createFacture(@RequestBody FactureDTO facture) {
        return factureService.create(facture);
    }

    // Only ChargeDossier updates facture content
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('CHARGEDOSSIER')")
    public FactureDTO updateFacture(@PathVariable Long id, @RequestBody FactureDTO facture) {
        facture.setIdFacture(id);
        return factureService.update(id, facture);
    }

    // Responsable validates a facture (sets status to VALIDEE)
    @PutMapping("/{id}/validate")
    @PreAuthorize("hasAnyRole('RESPONSABLE')")
    public FactureDTO validateFacture(@PathVariable Long id) {
        return factureService.validate(id);
    }

    // Responsable rejects a facture (sets status to REJETEE)
    @PutMapping("/{id}/reject")
    @PreAuthorize("hasAnyRole('RESPONSABLE')")
    public FactureDTO rejectFacture(@PathVariable Long id) {
        return factureService.reject(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('CHARGEDOSSIER')")
    public void deleteFacture(@PathVariable Long id) {
        factureService.delete(id);
    }
}
