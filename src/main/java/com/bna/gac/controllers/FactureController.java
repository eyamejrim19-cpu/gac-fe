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

    @PostMapping
    @PreAuthorize("hasAnyRole('CHARGEDOSSIER', 'RESPONSABLE')")
    public FactureDTO createFacture(@RequestBody FactureDTO facture) {
        return factureService.create(facture);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('CHARGEDOSSIER', 'RESPONSABLE')")
    public FactureDTO updateFacture(@PathVariable Long id, @RequestBody FactureDTO facture) {
        facture.setIdFacture(id);
        return factureService.update(id, facture);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('RESPONSABLE')")
    public void deleteFacture(@PathVariable Long id) {
        factureService.delete(id);
    }
}
