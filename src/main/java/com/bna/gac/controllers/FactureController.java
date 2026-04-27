package com.bna.gac.controllers;

import com.bna.gac.dto.FactureDTO;
import com.bna.gac.services.FactureService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/factures")
@RequiredArgsConstructor
public class FactureController {

    private final FactureService factureService;

    @GetMapping
    public List<FactureDTO> getAllFactures() {
        return factureService.getAll();
    }

    @GetMapping("/{id}")
    public FactureDTO getFactureById(@PathVariable Long id) {
        return factureService.getById(id);
    }

    @PostMapping
    public FactureDTO createFacture(@RequestBody FactureDTO facture) {
        return factureService.create(facture);
    }

    @PutMapping("/{id}")
    public FactureDTO updateFacture(@PathVariable Long id, @RequestBody FactureDTO facture) {
        facture.setIdFacture(id);
        return factureService.update(id, facture);
    }

    @DeleteMapping("/{id}")
    public void deleteFacture(@PathVariable Long id) {
        factureService.delete(id);
    }
}
